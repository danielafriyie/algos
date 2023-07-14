import typing


class Model:

    def __init__(self, status_code: int, message: str, data: typing.Optional[list[dict]] = None) -> None:
        self._status_code = status_code
        self._message = message
        self._data = data or []

    @property
    def status_code(self) -> int:
        return self._status_code

    @property
    def message(self) -> str:
        return self._message

    @property
    def data(self) -> list[dict]:
        return self._data

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}({str(self._data)})"
