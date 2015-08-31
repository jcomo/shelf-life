from bs4 import BeautifulSoup
from requests import Session

from shelf.models import ItemSearchResult, FoodItemGuide, ShelfLife
from shelf.exceptions import ItemNotFound


def _html_parser(html):
    return BeautifulSoup(html.encode('ascii', errors='ignore'), 'lxml')


def parse_search_results(html):
    parser = _html_parser(html)
    search_result_section = parser.find(class_='categorySearch')
    if not search_result_section:
        return []

    item_link_tags = search_result_section('a')
    return [ItemSearchResult(item.string.strip(), item.get('href')) for item in item_link_tags]


def parse_item_results(html):
    parser = _html_parser(html)

    storages = [method.string.strip() for method in parser(class_='slicedHead')]
    expirations = [expiration.string.strip() for expiration in parser(class_='days')]
    methods = [ShelfLife(time, storage) for time, storage in zip(expirations, storages)]

    tip_section = parser.find(class_='tips')
    if tip_section:
        tips = [tip.string.strip() for tip in tip_section('li')]
    else:
        tips = []

    return FoodItemGuide(methods, tips)


class StillTastyClient(object):
    def __init__(self, base_url=None):
        self.base_url = base_url or 'http://stilltasty.com'
        self.session = Session()

    def search(self, query):
        url = '{}/searchitems/search/{}'.format(self.base_url, query)
        response = self.session.get(url)
        return parse_search_results(response.text)

    def item_life(self, item_id):
        url = '{}/fooditems/index/{}'.format(self.base_url, item_id)
        response = self.session.get(url)
        results = parse_item_results(response.text)
        if not results:
            raise ItemNotFound('No item found for id: {}'.format(item_id))

        return results
