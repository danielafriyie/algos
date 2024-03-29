import typing

E = typing.TypeVar("E")


class Node(typing.Generic[E]):

    def __init__(
            self, element: E,
            next_: typing.Optional["Node[E]"] = None,
            previous: typing.Optional["Node[E]"] = None
    ) -> None:
        self._element = element
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
        return (f"{self.__class__.__name__}(element={self._element}, "
                f"next={self._next._element if self._next else None}, "
                f"previous={self._previous._element if self._previous else None})")


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
        elif previous is not None:
            previous.next = None
        node.next = None
        node.previous = None
        self._size -= 1

    def __iter__(self) -> typing.Iterator[Node[E]]:
        self._next = self._head
        return self

    def __next__(self) -> Node[E]:
        if self._next is None:
            raise StopIteration
        n = self._next
        self._next = n.next
        return n


class Queue(typing.Generic[E]):

    def __init__(self) -> None:
        self._list: DoublyLinkedList[E] = DoublyLinkedList()
        self._iterator: typing.Union[DoublyLinkedList[E], None] = None

    @property
    def size(self) -> int:
        return self._list.size

    @property
    def empty(self) -> bool:
        return self._list.empty

    def dequeue(self) -> E:
        head = self._list.head
        if head is None:
            return None
        next_ = head.next
        self._list.remove(head)
        self._list.head = None
        self._list.head = next_
        return head.element

    def first(self) -> E:
        head = self._list.head
        return head.element if head is not None else None

    def enqueue(self, element: E) -> None:
        self._list.append(Node(element))

    def __iter__(self) -> typing.Iterator[E]:
        self._iterator = iter(self._list)
        return self

    def __next__(self) -> E:
        return next(self._iterator).element


if __name__ == "__main__":
    queue: Queue[int] = Queue()
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    print(queue.size)
    print(queue.dequeue())
    print(queue.size)
    print()
    for e in queue:
        print(e)
