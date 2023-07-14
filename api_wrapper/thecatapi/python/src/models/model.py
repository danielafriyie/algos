import typing


class Model:
    """Base class for all models"""

    def __init__(self, id: int, **other_fields) -> None:
        self.id = id
        self._meta = None
        self.__dict__.update(other_fields)

    @property
    def meta(self) -> typing.Union[dict, None]:
        return self._meta

    @meta.setter
    def meta(self, meta: dict) -> None:
        self._meta = meta

    def __repr__(self) -> str:
        name = self.__class__.__name__
        kwargs = "\n".join([f"{k}={v}" for k, v in self.__dict__.items() if not k.startswith("_")])
        return f"{name}({kwargs})"
