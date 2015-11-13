from unittest import TestCase
from mock import Mock, call

from logging import Logger

from requests import Session, Response
from werkzeug.contrib.cache import SimpleCache

from shelf import exceptions
from shelf.client import StillTastyClient, StillTastyHTTPClient, StillTastyCachedClient


class TestCacheException(Exception):
    pass


class StillTastyHTTPClientTestCase(TestCase):
    def setUp(self):
        self.html = "<html></html>"
        fake_response = Mock(spec=Response)
        fake_response.text = self.html
        fake_response.status_code = 200

        self.session = Mock(spec=Session)
        self.session.get.return_value = fake_response
        self.client = StillTastyHTTPClient(base_url='http://example.com')
        self.client.session = self.session

    def test_it_returns_the_response_text_on_successful_request(self):
        html = self.client.fetch_item_page('123')
        self.assertEqual(self.html, html)

    def test_it_raises_on_unsuccessful_request(self):
        bad_response = Mock(spec=Response)
        bad_response.text = self.html
        bad_response.status_code = 500
        self.session.get.return_value = bad_response

        with self.assertRaises(exceptions.DatabaseUnreachable):
            self.client.search('watermelon')

    def test_it_raises_when_no_item_found(self):
        with self.assertRaises(exceptions.ItemNotFound):
            self.client.item_life('123')

    def test_it_requests_the_proper_search_url(self):
        self.client.fetch_search_page('watermelon')
        self.session.get.assert_called_once_with('http://example.com/searchitems/search/watermelon')

    def test_it_requests_the_proper_item_url(self):
        self.client.fetch_item_page('123')
        self.session.get.assert_called_once_with('http://example.com/fooditems/index/123')


class StillTastyCachedClientTestCase(TestCase):
    def setUp(self):
        self.logger = Mock(spec=Logger)
        self.cache = SimpleCache()
        self.client = Mock(spec=StillTastyClient)
        self.cached_client = StillTastyCachedClient(self.cache, TestCacheException, self.logger)
        self.cached_client.client = self.client

    def test_it_retrieves_from_the_cache_when_searching(self):
        query = 'watermelon'
        expected_result = 'result'
        self.client.search.return_value = expected_result

        search_results = self.cached_client.search(query)
        self.assertEqual(expected_result, search_results)
        self.client.search.assert_has_calls([call(query)])

        search_results = self.cached_client.search(query)
        self.assertEqual(expected_result, search_results)
        self.client.search.assert_has_calls([call(query)])

    def test_it_retrieves_from_the_cache_when_fetching_item(self):
        item_id = '123'
        expected_result = 'result'
        self.client.item_life.return_value = expected_result

        search_results = self.cached_client.item_life(item_id)
        self.assertEqual(expected_result, search_results)
        self.client.item_life.assert_has_calls([call(item_id)])

        search_results = self.cached_client.item_life(item_id)
        self.assertEqual(expected_result, search_results)
        self.client.item_life.assert_has_calls([call(item_id)])

    def test_it_logs_and_does_not_blow_up_on_cache_exception(self):
        exception = TestCacheException('Cache Exception')
        error_cache = Mock(spec=SimpleCache)
        error_cache.get.side_effect = exception
        self.cached_client.cache = error_cache

        self.cached_client.search('watermelon')

        self.logger.error.assert_called_once_with('Cache Exception')
