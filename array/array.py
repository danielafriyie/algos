import abc
import typing

E = typing.TypeVar("E")


class AbstractArray(abc.ABC, typing.Generic[E]):

    def __init__(self, size: int) -> None:
        self._size = size

    @abc.abstractmethod
    def length(self) -> int:
        pass

    @abc.abstractmethod
    def getitem(self, index: int) -> E:
        pass

    @abc.abstractmethod
    def setitem(self, index: int, value: E) -> None:
        pass

    @abc.abstractmethod
    def clearing(self, value: E) -> None:
        pass

    @abc.abstractmethod
    def iter(self) -> "AbstractArray":
        pass

    @abc.abstractmethod
    def next(self) -> E:
        pass

    def __iter__(self) -> "AbstractArray":
        return self.iter()

    def __next__(self) -> E:
        return self.next()

    def __len__(self) -> int:
        return self.length()
