import os

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

    def fetch_search_page(self, query):
        raise NotImplemented

    def fetch_item_page(self, item_id):
        raise NotImplemented

    def search(self, query):
        search_page = self.fetch_search_page(query)
        return parse_search_results(search_page)

    def item_life(self, item_id):
        item_page = self.fetch_item_page(item_id)
        results = parse_item_results(item_page)
        if not results:
            raise ItemNotFound('No item found for id: {}'.format(item_id))

        return results


class StillTastyFixtureClient(StillTastyClient):
    def __init__(self, fixture_path):
        self.fixture_path = fixture_path

    def fetch_search_page(self, query):
        return self._open_page('search.html')

    def fetch_item_page(self, item_id):
        return self._open_page('results.html')

    def _open_page(self, page_file):
        page_path = os.path.join(self.fixture_path, page_file)
        with open(page_path) as f:
            return f.read().decode('ascii', errors='ignore')


class StillTastyHTTPClient(StillTastyClient):
    def __init__(self, base_url=None):
        self.base_url = base_url or 'http://stilltasty.com'
        self.session = Session()

    def fetch_search_page(self, query):
        url = '{}/searchitems/search/{}'.format(self.base_url, query)
        return self._fetch_page(url)

    def fetch_item_page(self, item_id):
        url = '{}/fooditems/index/{}'.format(self.base_url, item_id)
        return self._fetch_page(url)

    def _fetch_page(self, url):
        response = self.session.get(url)
        return response.text
