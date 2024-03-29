import typing
from collections import deque

K = typing.TypeVar("K")
V = typing.TypeVar("V")


class Pair(typing.NamedTuple):
    key: K
    value: V


class HashMap(typing.Generic[K, V]):

    def __init__(self, capacity: typing.Optional[int] = 50, load_factor: typing.Optional[float] = 0.75) -> None:
        if capacity < 1:
            raise ValueError("Capacity must be a positive integer!")
        if not (0 < load_factor <= 1):
            raise ValueError("Load factor should be (0 < load_factor <= 1)")

        self._capacity = capacity
        self._load_factor = load_factor
        self._load_factor_multiplier = 2
        self._buckets: list[deque[Pair]] = [deque() for _ in range(self._capacity)]

    @classmethod
    def from_dict(cls, dictionary: dict[K, V], capacity: typing.Optional[int] = None) -> "HashMap":
        hash_table = cls(capacity or len(dictionary) * 10)
        for key, value in dictionary.items():
            hash_table[key] = value
        return hash_table

    @classmethod
    def from_hashtable(cls, hash_table: "HashMap[K, V]", capacity: typing.Optional[int] = 50) -> "HashMap":
        ht = cls(capacity)
        for key, value in hash_table.pairs:
            ht[key] = value
        return ht

    @property
    def size(self) -> int:
        return len(self)

    @property
    def empty(self) -> bool:
        return self.size < 1

    @property
    def pairs(self) -> list[Pair]:
        return [p for d in self._buckets for p in d]

    @property
    def keys(self) -> set[K]:
        return {p.key for p in self.pairs}

    @property
    def key_set(self) -> set[K]:
        return self.keys

    @property
    def values(self) -> list[V]:
        return [p.value for p in self.pairs]

    def get(self, key: K, default: typing.Optional[V] = None) -> V:
        try:
            value = self[key].value
        except KeyError:
            value = default
        return value

    def put(self, key: K, value: V) -> None:
        self[key] = value

    def remove(self, key: K) -> V:
        val = self[key]
        del self[key]
        return val

    def items(self) -> list[tuple[K, V]]:
        return [(p.key, p.value) for p in self.pairs]

    def entry_set(self) -> list[tuple[K, V]]:
        return self.items()

    def _hash(self, item: K) -> int:
        return hash(item) % self._capacity

    def _get_bucket(self, key: K) -> deque[Pair]:
        return self._buckets[self._hash(key)]

    def _load_factor_exceeded(self) -> bool:
        return (len(self) / self._capacity) >= self._load_factor

    def _resize_and_rehash(self) -> None:
        self._capacity *= self._load_factor_multiplier
        self._buckets = self.from_hashtable(self, self._capacity)._buckets

    def __len__(self) -> int:
        return sum(len(d) for d in self._buckets)

    def __str__(self):
        return "{" + ", ".join([f"{key!r}: {value!r}" for key, value in self.pairs]) + "}"

    def __repr__(self):
        cls = self.__class__.__name__
        return f"{cls}.from_dict({str(self)})"

    def __iter__(self) -> typing.Generator[K, None, None]:
        yield from self.keys

    def __getitem__(self, key: K) -> V:
        bucket = self._get_bucket(key)
        for pair in bucket:
            if pair.key == key:
                return pair.value
        raise KeyError(key)

    def __setitem__(self, key: K, value: V) -> None:
        if self._load_factor_exceeded():
            self._resize_and_rehash()

        bucket = self._get_bucket(key)
        for index, pair in enumerate(bucket):
            if pair.key == key:
                bucket[index] = Pair(key, value)
                break
        else:
            bucket.append(Pair(key, value))

    def __delitem__(self, key: K) -> None:
        bucket = self._get_bucket(key)
        for index, pair in enumerate(bucket):
            if pair.key == key:
                del bucket[index]
                break
        else:
            raise KeyError(key)

    def __contains__(self, key: K) -> bool:
        try:
            self[key]
        except KeyError:
            return False
        return True


if __name__ == "__main__":
    h: HashMap[str, int] = HashMap()
    h["hey"] = 23
    h["five"] = 5
    h["easy"] = 0
    h["difficult"] = 100
    print(h)
