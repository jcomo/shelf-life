from datetime import timedelta
from unittest import TestCase

from shelf.models import ItemSearchResult, ShelfLife, FoodItemGuide


class ItemSearchResultTestCase(TestCase):
    def test_it_extracts_item_id_from_url(self):
        item = ItemSearchResult('Watermelon', 'http://example.com/items/123')
        self.assertEqual('123', item.item_id)


class ShelfLifeTestCase(TestCase):
    _DAY_IN_SECONDS = int(timedelta(days=1).total_seconds())

    def assertExpirationConversionEqual(self, expiration, expected_time_in_seconds):
        self.assertEqual(expected_time_in_seconds, ShelfLife(expiration, None).time_in_seconds())

    def test_when_no_expiration_it_returns_no_time_in_seconds(self):
        self.assertExpirationConversionEqual('never', None)

    def test_it_converts_expiration_string_in_days_to_time_in_seconds(self):
        self.assertExpirationConversionEqual('1 day', self._DAY_IN_SECONDS)
        self.assertExpirationConversionEqual('2 days', self._DAY_IN_SECONDS * 2)

    def test_it_converts_expiration_string_in_weeks_to_time_in_seconds(self):
        self.assertExpirationConversionEqual('1 week', self._DAY_IN_SECONDS * 7)
        self.assertExpirationConversionEqual('2 weeks', self._DAY_IN_SECONDS * 7 * 2)

    def test_it_converts_expiration_string_in_months_to_time_in_seconds(self):
        self.assertExpirationConversionEqual('1 month', self._DAY_IN_SECONDS * 30)
        self.assertExpirationConversionEqual('2 months', self._DAY_IN_SECONDS * 30 * 2)

    def test_it_converts_expiration_string_in_years_to_time_in_seconds(self):
        self.assertExpirationConversionEqual('1 year', self._DAY_IN_SECONDS * 365)
        self.assertExpirationConversionEqual('2 years', self._DAY_IN_SECONDS * 365 * 2)


class FoodItemGuideTestCase(TestCase):
    def test_it_is_falsey_with_no_storage_methods(self):
        self.assertFalse(FoodItemGuide('Watermelon', [], []))

    def test_it_is_truthy_with_storage_methods(self):
        self.assertTrue(FoodItemGuide('Watermelon', ['Freezer'], []))
