class ShelfLifeException(Exception):
    status_code = 500


class ItemNotFound(ShelfLifeException):
    status_code = 404
