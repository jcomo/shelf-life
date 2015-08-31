from shelf.config import Environment, Configuration


if __name__ == '__main__':
    Configuration.SERVICE_ENVIRONMENT = Environment.DEV
    Configuration.DEBUG = True

    from shelf.app import app
    app.run(host='0.0.0.0', port=9000)
