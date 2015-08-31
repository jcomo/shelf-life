from flask import Flask, request, jsonify
from requests import Session

from shelf.config import Configuration, Environment, running_in
from shelf.client import StillTastyHTTPClient, StillTastyFixtureClient
from shelf.models import StillTastyJSONEncoder
from shelf.exceptions import ShelfLifeException

app = Flask('shelf-life')
app.config.from_object(Configuration)
app.json_encoder = StillTastyJSONEncoder

if running_in(Environment.TEST):
    client = StillTastyFixtureClient('fixtures')
else:
    client = StillTastyHTTPClient()


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
    response = jsonify({
        'status': e.status_code,
        'message': e.message,
    })

    response.status_code = e.status_code
    return response
