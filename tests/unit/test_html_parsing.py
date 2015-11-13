from unittest import TestCase

from shelf.client import parse_search_results, parse_item_results


class HTMLParsingTestCase(TestCase):
    def test_with_no_search_results_it_returns_empty_list(self):
        HTML = "<html></html>"
        self.assertEqual([], parse_search_results(HTML))

    def test_with_search_results_it_returns_results(self):
        HTML = """
        <html>
            <div class="categorySearch">
                <a href="/items/1">Watermelon Cut</a>
                <a href="/items/2">Watermelon Whole</a>
            </div>
        </html>
        """

        results = parse_search_results(HTML)

        cut_watermelon = results[0]
        self.assertEqual("Watermelon Cut", cut_watermelon.name)
        self.assertEqual("/items/1", cut_watermelon.url)

        whole_watermelon = results[1]
        self.assertEqual("Watermelon Whole", whole_watermelon.name)
        self.assertEqual("/items/2", whole_watermelon.url)

    def test_it_returns_item_results(self):
        HTML = """
        <html>
            <div class="bigBlackHeading">
                Watermelon Cut
            </div>
            <div>
                <div class="slicedHead">
                    Refrigerator
                </div>
                <div class="days">
                    4 days
                </div>
            </div>
            <div>
                <div class="slicedHead">
                    Freezer
                </div>
                <div class="days">
                    9 months
                </div>
            </div>
            <ul class="tips">
                <li>Store in plastic wrap</li>
            </ul>
        </html>
        """
        result = parse_item_results(HTML)

        self.assertEqual("Watermelon Cut", result.name)

        refrigerator = result.methods[0]
        self.assertEqual("Refrigerator", refrigerator.storage)
        self.assertEqual("4 days", refrigerator.expiration)

        freezer = result.methods[1]
        self.assertEqual("Freezer", freezer.storage)
        self.assertEqual("9 months", freezer.expiration)

        self.assertEqual(["Store in plastic wrap"], result.tips)

    def test_name_is_blank_when_missing(self):
        HTML = "<html></html>"
        result = parse_item_results(HTML)
        self.assertEqual('', result.name)

    def test_properties_are_empty_lists_when_none_exist(self):
        HTML = "<html></html>"
        result = parse_item_results(HTML)

        self.assertEqual([], result.methods)
        self.assertEqual([], result.tips)
