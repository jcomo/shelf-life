from logging import StreamHandler, Formatter, ERROR
from logging.handlers import RotatingFileHandler

_FORMATTER = Formatter('[%(name)s:%(levelname)s] %(asctime)s -- %(message)s')


def stream_handler(config):
    handler = StreamHandler()
    handler.setLevel(config['LOG_LEVEL'])
    handler.setFormatter(_FORMATTER)
    return handler


def file_handler(config):
    file_name = config['LOG_FILE_NAME']
    file_size = config['LOG_FILE_SIZE']
    backup_count = config['LOG_BACKUP_COUNT']

    handler = RotatingFileHandler(file_name, maxBytes=file_size, backupCount=backup_count)
    handler.setLevel(ERROR)
    handler.setFormatter(_FORMATTER)
    return handler
