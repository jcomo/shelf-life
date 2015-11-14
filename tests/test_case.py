from flask_testing import TestCase

from shelf.app import app


class APITestCase(TestCase):
    def create_app(self):
        return app
