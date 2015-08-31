from uuid import uuid4 as guid
from traceback import format_exc

from flask import Flask, request, jsonify
from requests import Session

from shelf.loggers import stream_handler, file_handler
from shelf.config import Configuration, Environment, running_in
from shelf.client import StillTastyHTTPClient, StillTastyFixtureClient
from shelf.models import StillTastyJSONEncoder
from shelf.exceptions import ShelfLifeException


class ShelfLifeApi(Flask):
    def log_exception(self, exc_info):
        pass


app = ShelfLifeApi('shelf-life')
app.config.from_object(Configuration)

app.json_encoder = StillTastyJSONEncoder

if not running_in(Environment.TEST):
    app.logger.addHandler(stream_handler(app.config))
    app.logger.addHandler(file_handler(app.config))
    client = StillTastyHTTPClient()
else:
    client = StillTastyFixtureClient('fixtures')


@app.route('/search')
def search():
    query = request.args.get('q')
    search_results = client.search(query)
    return jsonify({
        'query': query,
        'results': search_results,
    })


@app.route('/items/<int:item_id>')
def shelf_life(item_id):
    item_results = client.item_life(item_id)
    return jsonify({
        'id': item_id,
        'results': item_results,
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
