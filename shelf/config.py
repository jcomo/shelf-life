import os
import logging


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

    LOG_LEVEL = os.environ.get('LOG_LEVEL', logging.DEBUG)
    LOG_FILE_NAME = os.environ.get('LOG_FILE_NAME', 'error.log')
    LOG_FILE_SIZE = os.environ.get('LOG_FILE_SIZE', 1024 ** 2)
    LOG_BACKUP_COUNT = os.environ.get('LOG_BACKUP_COUNT', 5)

    SERVER_NAME = os.environ.get('SERVER_NAME')
    SERVICE_ENVIRONMENT = Environment.read()

    REDIS_HOST = os.environ.get('REDIS_HOST', 'localhost')
    REDIS_PORT = os.environ.get('REDIS_PORT', 6379)
    CACHE_PREFIX = os.environ.get('CACHE_PREFIX', 'ShelfLife::')
