import typing

E = typing.TypeVar("E")
Number = typing.Union[int, float]


class Node(typing.Generic[E]):

    def __init__(
            self,
            element: E,
            key: Number,
            next_: typing.Optional["Node[E]"] = None,
            previous: typing.Optional["Node[E]"] = None
    ) -> None:
        self._element = element
        self._key = key
        self._next = next_
        self._previous = previous

    @property
    def next(self) -> typing.Union["Node[E]", None]:
        return self._next

    @next.setter
    def next(self, node: "Node[E]") -> None:
        self._next = node
        if node is not None:
            node._previous = self

    @property
    def previous(self) -> typing.Union["Node[E]", None]:
        return self._previous

    @previous.setter
    def previous(self, node: "Node[E]") -> None:
        self._previous = node
        if node is not None:
            node._next = self

    @property
    def element(self) -> E:
        return self._element

    @element.setter
    def element(self, element: E) -> None:
        self._element = element

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}(element={self._element}, key={self._key})"

    def __lt__(self, other: "Node[E]") -> bool:
        return self._key < other._key

    def __gt__(self, other: "Node[E]") -> bool:
        return self._key > other._key


class DoublyLinkedList(typing.Generic[E]):

    def __init__(
            self,
            head: typing.Optional[Node[E]] = None,
            tail: typing.Optional[Node[E]] = None
    ) -> None:
        self._head = head
        self._tail = tail
        self._size = 0
        self._next: typing.Union[Node[E], None] = None

    @property
    def size(self) -> int:
        return self._size

    @property
    def head(self) -> typing.Union[Node[E], None]:
        return self._head

    @head.setter
    def head(self, node: Node[E]) -> None:
        if self._head is not None:
            self._head.previous = node
        self._head = node

    @property
    def tail(self) -> typing.Union[Node[E], None]:
        return self._tail

    @tail.setter
    def tail(self, node: Node[E]) -> None:
        if self._tail is not None:
            self._tail.next = node
        self._tail = node

    @property
    def empty(self) -> bool:
        return self._size < 1

    def append(self, node: Node[E]) -> None:
        self.tail = node
        if self._head is None:
            self.head = node
        self._size += 1

    def prepend(self, node: Node[E]) -> None:
        self.head = node
        if self._tail is None:
            self.tail = node
        self._size += 1

    def remove(self, node: Node[E]) -> None:
        next_ = node.next
        previous = node.previous
        if next_ is not None:
            next_.previous = previous
            if node == self._head:
                self._head = next_
        elif previous is not None:
            previous.next = None
            if node == self._tail:
                self._tail = previous
        node.next = None
        node.previous = None
        self._size -= 1

    def insert_before(self, node1: Node[E], node2: Node[E]) -> None:
        """
        Inserts node2 before node1.
        Raises ValueEror if node2 has next or previous.
        :param node1: inserts (comes) after node2
        :param node2: inserts before node1
        :return: None
        :raises: ValueError
        """
        if node2.previous or node2.next:
            raise ValueError
        if node1.previous:
            node1.previous.next = node2
        node2.next = node1
        if node1 == self._head:
            self._head = node2

    def inesrt_after(self, node1: Node[E], node2: Node[E]) -> None:
        """
        Inserts node2 after node1.
        Raises ValueEror if node2 has next or previous.
        :param node1: inserts (comes) before node2
        :param node2: inserts after node1
        :return: None
        :raises: ValueError
        """
        if node2.previous or node2.next:
            raise ValueError
        if node1.next:
            node1.next.previous = node2
        node1.next = node2
        if self._tail == node1:
            self._tail = node2

    def __iter__(self) -> "DoublyLinkedList":
        self._next = self._head
        return self

    def __next__(self) -> Node[E]:
        if self._next is None:
            raise StopIteration
        n = self._next
        self._next = n.next
        return n


class PriorityQueue(typing.Generic[E]):

    def __init__(self) -> None:
        self._list: DoublyLinkedList[E] = DoublyLinkedList()

    @property
    def size(self) -> int:
        return self._list.size

    @property
    def empty(self) -> bool:
        return self._list.empty

    def insert(self, key: Number, element: E) -> Node[E]:
        new = Node(element, key)
        if self._list.empty:
            self._list.append(new)
        else:
            node = self._list.head
            while node is not None:
                if new > node:
                    self._list.insert_before(node, new)
                    break
                node = node.next
        return new

    def min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        return self._list.tail

    def remove_min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        node = self.min()
        self._list.remove(node)
        return node

    def __str__(self) -> str:
        return str([n for n in self._list])


if __name__ == "__main__":
    queue: PriorityQueue[int] = PriorityQueue()
    queue.insert(0, 324)
    queue.insert(1, 53)
    queue.insert(5, 23)
    queue.insert(4, 32)
    queue.insert(10, 2)
    queue.insert(3, 23234)
    print(queue.size)
    print(queue.min())
    print(queue)
