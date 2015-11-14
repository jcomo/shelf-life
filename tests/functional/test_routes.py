from flask import url_for

from tests.test_case import APITestCase

from shelf.models import ShelfLife


class BadRequestTestCase(APITestCase):
    def test_json_is_returned_on_bad_request(self):
        response = self.client.get('/admin.php')

        self.assertEqual(404, response.status_code)
        self.assertDictEqual(response.json, {
            'status': 404,
            'message': 'Not Found',
        })


class StatusTestCase(APITestCase):
    def test_status_returns_metadata_about_running_application(self):
        response = self.client.get(url_for('status'))

        self.assertEqual(200, response.status_code)
        data = response.json

        self.assertRegexpMatches(data['hash'], r'^[0-9a-f]{40}$')
        self.assertEqual(data['debug'], False)
        self.assertEqual(data['environment'], 'test')


class SearchTestCase(APITestCase):
    def test_search_returns_results(self):
        query = 'watermelon'
        response = self.client.get(url_for('search'), query_string={'q': query})

        self.assertEqual(200, response.status_code)
        self.assertDictEqual(response.json, {
            'query': query,
            'results': [
                {
                    'id': 18665,
                    'name': 'Watermelon Fresh, Raw, Cut Up',
                    'url': 'http://localhost/items/18665',
                },
                {
                    'id': 18666,
                    'name': 'Watermelon Fresh, Raw, Whole',
                    'url': 'http://localhost/items/18666',
                },
            ],
        })


class ItemResourceTestCase(APITestCase):
    def test_items_resource_returns_data_about_specific_item(self):
        item_id = 18665
        response = self.client.get(url_for('shelf_life', item_id=item_id))

        expected_fridge_storage = ShelfLife('3-4 days', 'Refrigerator')
        expected_freezer_storage = ShelfLife('10-12 months', 'Freezer')

        self.assertEqual(200, response.status_code)
        data = response.json

        # Don't really care what the tips are, we just know they should be there
        tips = response.json['data'].pop('tips', [])
        self.assertTrue(len(tips) > 0)

        self.assertDictEqual(response.json, {
            'id': item_id,
            'data': {
                'name': 'Watermelon Fresh, Raw, Cut Up',
                'methods': [
                    {
                        'expiration': expected_fridge_storage.expiration,
                        'storage': expected_fridge_storage.storage,
                        'time': expected_fridge_storage.time_in_seconds(),
                    },
                    {
                        'expiration': expected_freezer_storage.expiration,
                        'storage': expected_freezer_storage.storage,
                        'time': expected_freezer_storage.time_in_seconds(),
                    },
                ],
            },
        })
