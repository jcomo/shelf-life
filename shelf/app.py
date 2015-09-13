from signal import signal, SIGUSR1
from uuid import uuid4 as guid
from traceback import format_exc

from flask import Flask, request, jsonify
from requests import Session

from werkzeug.contrib.cache import RedisCache
from redis import Redis, RedisError

from shelf.loggers import stream_handler, file_handler
from shelf.config import Configuration, Environment, running_in
from shelf.client import StillTastyCachedClient, StillTastyFixtureClient
from shelf.models import ItemSearchResultSchema, FoodItemGuideSchema
from shelf.exceptions import ShelfLifeException


def cache_flush_handler(cache, logger):
    def _handler(signum, frame):
        logger.info('Flushing cache...')
        cache.clear()

    return _handler


class ShelfLifeApi(Flask):
    def log_exception(self, exc_info):
        pass


app = ShelfLifeApi('shelf-life')
app.config.from_object(Configuration)

if not running_in(Environment.TEST):
    app.logger.setLevel(app.config['LOG_LEVEL'])
    app.logger.addHandler(stream_handler(app.config))
    app.logger.addHandler(file_handler(app.config))
    cache = RedisCache(Redis.from_url(app.config['REDIS_URL']),
                       key_prefix=app.config['CACHE_PREFIX'])
    client = StillTastyCachedClient(cache, RedisError, app.logger)

    # Set up a signal to flush the cache of all app-related data
    signal(SIGUSR1, cache_flush_handler(cache, app.logger))
else:
    client = StillTastyFixtureClient('fixtures')


@app.route('/')
def status():
    return jsonify({
        'hash': Configuration.HASH,
        'debug': Configuration.DEBUG,
        'environment': Configuration.SERVICE_ENVIRONMENT,
    })


@app.route('/search')
def search():
    query = request.args.get('q')
    search_results = client.search(query)
    serializer = ItemSearchResultSchema()
    return jsonify({
        'query': query,
        'results': serializer.dump(search_results, many=True).data,
    })


@app.route('/items/<int:item_id>')
def shelf_life(item_id):
    item_result = client.item_life(item_id)
    serializer = FoodItemGuideSchema()
    return jsonify({
        'id': item_id,
        'results': serializer.dump(item_result).data,
    })


@app.errorhandler(ShelfLifeException)
def handle_custom_exception(e):
    return _error_response(e.status_code, e.message)


@app.errorhandler(400)
@app.errorhandler(401)
@app.errorhandler(403)
@app.errorhandler(404)
def handle_http_error(e):
    return _error_response(e.code, e.name)


@app.errorhandler(500)
def handle_server_error(e):
    error_id = str(guid())
    app.logger.error('{}\n{}'.format(error_id, format_exc()))
    return _error_response(500, 'Internal Server Error', error_id)


def _error_response(code, message, error_id=None):
    response_data = {
        'status': code,
        'message': message,
    }

    if error_id:
        response_data['error_id'] = error_id

    response = jsonify(response_data)
    response.status_code = code
    return response
