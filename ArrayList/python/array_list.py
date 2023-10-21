import ctypes
import typing

E = typing.TypeVar("E")


class ArrayList(typing.Generic[E]):
    DEFAULT_CAPACITY = 10
    DEFAULT_MULTIPLIER = 2
    DEFAULT_LOAD_FACTOR = 0.85
    DEFAULT_SHRINK_FACTOR = 0.25

    def __init__(
            self,
            capacity: typing.Optional[int] = None,
            multiplier: typing.Optional[int] = None,
            load_factor: typing.Optional[float] = None,
            shrink_factor: typing.Optional[float] = None
    ) -> None:
        if ((load_factor is not None) and (load_factor > 1)) or ((shrink_factor is not None) and (shrink_factor > 1)):
            raise ValueError("Load or shrink factor should be less than or equal to 1")
        self._capacity = capacity or self.DEFAULT_CAPACITY
        self._multiplier = multiplier or self.DEFAULT_MULTIPLIER
        self._load_factor = load_factor or self.DEFAULT_LOAD_FACTOR
        self._shrink_factor = shrink_factor or self.DEFAULT_SHRINK_FACTOR
        self._size = 0
        self._array = self._create_array(self._capacity)

    @property
    def size(self) -> int:
        return self._size

    @property
    def empty(self) -> bool:
        return self._size <= 0

    @staticmethod
    def _create_array(capacity: int) -> ctypes.Array[E]:
        f = ctypes.py_object * capacity
        arr = f()
        return arr

    @staticmethod
    def _check_size(index: int, size: int) -> None:
        if index >= size:
            raise IndexError()

    def _resize(self, capacity: int) -> None:
        array = self._create_array(capacity)
        for i in range(self._size):
            array[i] = self._array[i]
        self._array = array

    def _grow(self) -> None:
        if (self._size / len(self._array)) >= self._load_factor:
            self._capacity *= self._multiplier
            self._resize(self._capacity)

    def _shrink(self) -> None:
        n = int(self._shrink_factor * len(self._array))
        if self._size < n:
            self._capacity = n
            self._resize(self._capacity)

    def _insert(self, index: int, element: E) -> None:
        self._grow()
        if index == self._size:
            self._array[self._size] = element
        else:
            for i in range(self._size, index, -1):
                self._array[i] = self._array[self._size - 1]
            self._array[index] = element
        self._size += 1

    def get(self, index: int) -> E:
        self._check_size(index, self._size)
        return self._array[index]

    def set(self, index: int, element: E) -> E:
        self._check_size(index, self._size)
        current_elm = self._array[index]
        self._array[index] = element
        return current_elm

    def add(self, index: int, element: E) -> None:
        self._check_size(index, self._size + 1)
        self._insert(index, element)

    def append(self, element: E) -> None:
        # Adds element to the end of the list
        self.add(self._size, element)

    def remove(self, index: int) -> E:
        self._check_size(index, self._size)
        current_elm = self._array[index]
        for i in range(index, self._size):
            if (i + 1) == self._size:
                self._array[i] = None
            else:
                self._array[i] = self._array[i + 1]
        self._size -= 1
        self._shrink()
        return current_elm

    def __repr__(self) -> str:
        return str([self._array[i] for i in range(self._size)])


if __name__ == "__main__":
    a: ArrayList[int] = ArrayList()
    a.append(1)
    a.add(0, 2)
    print(a.get(0))
    print(a)
    for n in range(50):
        a.append(n + 3)
    print(a)
    for n in range(50):
        a.remove(0)
    print(a)
    print(a.size)
