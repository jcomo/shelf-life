from argparse import ArgumentParser

from shelf.config import Environment, Configuration


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('-d', '--debug', action='store_true',
                        help='Use this flag to run in debug mode')
    parser.add_argument('-p', '--port', type=int, default=9000,
                        help='The port to run the server on')

    args = parser.parse_args()

    Configuration.SERVICE_ENVIRONMENT = Environment.DEV
    Configuration.DEBUG = args.debug

    from shelf.app import app
    app.run(host='0.0.0.0', port=args.port)
