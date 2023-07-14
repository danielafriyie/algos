class Model:
    """Base class for all models"""

    def __init__(self, id: int, **other_fields) -> None:
        self.id = id
        self.__dict__.update(other_fields)
