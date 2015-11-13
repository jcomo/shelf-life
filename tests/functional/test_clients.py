from unittest import TestCase
from mock import Mock, call

from logging import Logger

from werkzeug.contrib.cache import SimpleCache

from shelf.client import StillTastyClient, StillTastyCachedClient


class TestCacheException(Exception):
    pass


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
