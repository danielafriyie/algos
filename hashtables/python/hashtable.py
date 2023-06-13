import typing

SENTINEL = None

K = typing.TypeVar("K")
V = typing.TypeVar("V")


class Pair(typing.NamedTuple):
    key: K
    value: V


class HashTable(typing.Generic[K, V]):

    def __init__(self, capacity: int) -> None:
        self._capacity = capacity
        self._pairs: list[Pair] = self._capacity * [SENTINEL]

    @classmethod
    def from_dict(cls, dictionary: dict[typing.Any, typing.Any], capacity: typing.Optional[int] = None) -> "HashTable":
        hash_table = cls(capacity or len(dictionary) * 10)
        for key, value in dictionary.items():
            hash_table[key] = value
        return hash_table

    @property
    def pairs(self) -> list[Pair]:
        return [p for p in self._pairs if p != SENTINEL]

    @property
    def keys(self) -> set[K]:
        return {p.key for p in self.pairs}

    @property
    def values(self) -> list[V]:
        return [p.value for p in self.pairs]

    def get(self, key: K, default: typing.Optional[V] = None) -> V:
        try:
            value = self[key].value
        except KeyError:
            value = default
        return value

    def hash(self, item: K) -> int:
        return hash(item) % self._capacity

    def __len__(self) -> int:
        return len(self.pairs)

    def __str__(self):
        pairs = []
        for key, value in self.pairs:
            pairs.append(f"{key!r}: {value!r}")
        return "{" + ", ".join(pairs) + "}"

    def __repr__(self):
        cls = self.__class__.__name__
        return f"{cls}.from_dict({str(self)})"

    def __iter__(self) -> typing.Generator[K, None, None]:
        yield from self.keys

    def __getitem__(self, key: K) -> V:
        pair = self._pairs[self.hash(key)]
        if pair is SENTINEL:
            raise KeyError(key)
        return pair.value

    def __setitem__(self, key: K, value: V) -> None:
        self._pairs[self.hash(key)] = Pair(key, value)

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
    print(h)
