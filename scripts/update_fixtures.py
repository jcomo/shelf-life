import os

from shelf.client import StillTastyHTTPClient


def write_page(page_data, filename):
    path = os.path.join('fixtures', filename)
    with open(path, 'w') as f:
        f.write(page_data.encode('ascii', errors='ignore'))


if __name__ == '__main__':
    client = StillTastyHTTPClient()
    write_page(client.fetch_search_page('water'), 'search.html')
    write_page(client.fetch_item_page(18655), 'results.html')
