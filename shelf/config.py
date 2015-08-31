import os


def running_in(environment):
    return Configuration.SERVICE_ENVIRONMENT == environment


class Environment(object):
    PROD = 'production'
    DEV = 'development'
    TEST = 'test'

    _valid_environments = [PROD, DEV, TEST]

    @classmethod
    def read(cls):
        environment = os.environ.get('SERVICE_ENVIRONMENT', '').lower()
        if environment not in cls._valid_environments:
            environment = cls.PROD

        return environment


class Configuration(object):
    DEBUG = False

    SERVER_NAME = os.environ.get('SERVER_NAME')
    SERVICE_ENVIRONMENT = Environment.read()
