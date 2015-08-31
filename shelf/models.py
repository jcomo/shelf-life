import re

from flask import url_for
from flask.json import JSONEncoder


class ItemSearchResult(object):
    def __init__(self, name, url):
        self.name = name
        self.url = url
        self.item_id = self._pluck_item_id(url)

    @staticmethod
    def _pluck_item_id(url):
        return url.split('/')[-1]


class ShelfLife(object):
    _EXPIRATION_TIME_PATTERN = re.compile(r'^(\d+)(-\d+)? (days?|weeks?|months?|years?)')

    def __init__(self, time, storage):
        self.time = time
        self.storage = storage

    def time_in_seconds(self):
        match = self._EXPIRATION_TIME_PATTERN.match(self.time)
        if match:
            amount = int(match.group(1))
            unit = match.group(3)

            # Start by assuming we are in days. Use `in' to account for plural
            amount_by_day = amount * 60 * 60 * 24
            if 'week' in unit:
                amount_by_day *= 7
            elif 'month' in unit:
                amount_by_day *= 30
            elif 'year' in unit:
                amount_by_day *= 365

            return amount_by_day


class FoodItemGuide(object):
    def __init__(self, methods, tips):
        self.methods = methods
        self.tips = tips


class StillTastyJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, ItemSearchResult):
            return {
                'id': obj.item_id,
                'name': obj.name,
                'url': url_for('shelf_life', item_id=obj.item_id, _external=True),
            }

        elif isinstance(obj, FoodItemGuide):
            return {
                'methods': obj.methods,
                'tips': obj.tips,
            }

        elif isinstance(obj, ShelfLife):
            return {
                'expiration': obj.time,
                'storage': obj.storage,
                'time': obj.time_in_seconds(),
            }

        return super(StillTastyJSONEncoder, self).default(obj)
