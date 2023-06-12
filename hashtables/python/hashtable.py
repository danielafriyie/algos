import typing

SENTINEL = object()

K = typing.TypeVar("K")
V = typing.TypeVar("V")


class HashTable(typing.Generic[K, V]):

    def __init__(self, capacity: int) -> None:
        self._capacity = capacity
        self._values = self._capacity * [SENTINEL]

    def hash(self, item: K) -> int:
        return hash(item) % self._capacity

    def __len__(self) -> int:
        return self._capacity

    def __getitem__(self, key: K) -> V:
        value = self._values[self.hash(key)]
        if value is SENTINEL:
            raise KeyError(key)
        return value

    def __setitem__(self, key: K, value: V) -> None:
        self._values[self.hash(key)] = value

    def __delitem__(self, key: K) -> None:
        if key not in self:
            raise KeyError(key)
        self[key] = SENTINEL

    def __contains__(self, key: K) -> bool:
        try:
            self[key]
        except KeyError:
            return False
        return True


if __name__ == "__main__":
    h: HashTable[str, int] = HashTable(10)
    h["hey"] = 23
    print(h["hey"])
    h["hey"] = 33
