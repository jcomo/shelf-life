from unittest import TestCase

from shelf.client import StillTastyHTTPClient


class StillTastyTestCase(TestCase):
    def setUp(self):
        self.client = StillTastyHTTPClient()

    def test_it_returns_search_results(self):
        results = self.client.search('watermelon')

        self.assertNotEqual([], results, "Expected results but got none")
        for result in results:
            self.assertIn('watermelon', result.name.lower())
            self.assertIsNotNone(result.item_id, "Expected result to have an item id")

    def test_it_returns_item_results(self):
        item_id = '18665'
        item = self.client.item_life(item_id)

        self.assertEqual("Watermelon Fresh, Raw, Cut Up", item.name)
        self.assertNotEqual([], item.methods, "Expected the item to have storage methods but got none")
        self.assertNotEqual([], item.tips, "Expected the item to have storage tips but got none")
