import re

from marshmallow import Schema, fields
from inflection import titleize

from flask import url_for
from flask.json import JSONEncoder

_NAME_SPACE_PATTERN = re.compile('\s\s+')


def _clean_item_name(name):
    space_squashed_name = re.sub(_NAME_SPACE_PATTERN, ' ', name)
    return titleize(space_squashed_name)


class ItemSearchResult(object):
    def __init__(self, name, url):
        self.name = _clean_item_name(name)
        self.url = url
        self.item_id = self._pluck_item_id(url)

    def __repr__(self):
        return '<ItemSearchResult [{self.item_id!s}] {self.name!r}>'.format(self=self)

    @staticmethod
    def _pluck_item_id(url):
        return url.split('/')[-1]


class ItemSearchResultSchema(Schema):
    id = fields.Int(attribute='item_id')
    name = fields.Str()
    url = fields.Method('external_url')

    def external_url(self, result):
        return url_for('shelf_life', item_id=result.item_id, _external=True)


class ShelfLife(object):
    _EXPIRATION_TIME_PATTERN = re.compile(r'^(\d+)(-\d+)? (days?|weeks?|months?|years?)')

    def __init__(self, expiration, storage):
        self.expiration = expiration
        self.storage = storage

    def __repr__(self):
        return '<ShelfLife: {self.storage!s} - {self.expiration!s}>'.format(self=self)

    def time_in_seconds(self):
        match = self._EXPIRATION_TIME_PATTERN.match(self.expiration)
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


class ShelfLifeSchema(Schema):
    storage = fields.Str()
    expiration = fields.Str()
    time = fields.Function(lambda x, ctx: x.time_in_seconds())


class FoodItemGuide(object):
    def __init__(self, name, methods, tips):
        self.name = _clean_item_name(name)
        self.methods = methods
        self.tips = tips

    def __nonzero__(self):
        return bool(self.methods)

    def __repr__(self):
        return '<FoodItemGuide {self.name!r}>'.format(self=self)


class FoodItemGuideSchema(Schema):
    name = fields.Str()
    methods = fields.Nested(ShelfLifeSchema, many=True)
    tips = fields.Raw()
