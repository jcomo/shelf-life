import os
import logging
from urlparse import urlparse
from subprocess import check_output, CalledProcessError


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


def _git_hash():
    app_dir = os.path.dirname(os.path.realpath(__file__))
    command = ['git', '-C', app_dir, 'rev-parse', 'HEAD']

    try:
        return check_output(command).strip()
    except (CalledProcessError, OSError):
        return "unknown"


class Configuration(object):
    DEBUG = False
    HASH = _git_hash()

    JSONIFY_PRETTYPRINT_REGULAR = DEBUG

    LOG_LEVEL = os.environ.get('LOG_LEVEL', logging.DEBUG)
    LOG_FILE_NAME = os.environ.get('LOG_FILE_NAME', 'error.log')
    LOG_FILE_SIZE = os.environ.get('LOG_FILE_SIZE', 1024 ** 2)
    LOG_BACKUP_COUNT = os.environ.get('LOG_BACKUP_COUNT', 5)

    SERVER_NAME = os.environ.get('SERVER_NAME')
    SERVICE_ENVIRONMENT = Environment.read()

    REDIS_URL = os.environ.get('REDIS_URL', 'redis://localhost')
    CACHE_PREFIX = os.environ.get('CACHE_PREFIX', 'ShelfLife::')
