import abc
import ctypes
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
    def clear(self, value: E) -> None:
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

    def __getitem__(self, index: int) -> E:
        return self.getitem(index)

    def __setitem__(self, index: int, item: E) -> None:
        self.setitem(index, item)


class Array(AbstractArray[E]):

    def __init__(self, size: int) -> None:
        assert size > 0, "Array size must > 0"
        super().__init__(size)
        self._arr = ctypes.py_object * size
        self._elements = self._arr()
        self._iter_index = 0

    def _assert_index(self, index: int) -> None:
        assert 0 <= index <= self._size, "Index out of range!"

    def length(self) -> int:
        return self._size

    def getitem(self, index: int) -> E:
        self._assert_index(index)
        return self._elements[index]

    def setitem(self, index: int, value: E) -> None:
        self._assert_index(index)
        self._elements[index] = value

    def clear(self, value: E) -> None:
        for i in range(self._size):
            self._elements[i] = value

    def iter(self) -> "AbstractArray":
        self._iter_index = 0
        return self

    def next(self) -> E:
        if self._iter_index >= self._size:
            raise StopIteration
        element = self._elements[self._iter_index]
        self._iter_index += 1
        return element


if __name__ == "__main__":
    arr: Array[int] = Array(5)
    for i in range(5):
        arr[i] = i * 100

    for d in arr:
        print(d)
