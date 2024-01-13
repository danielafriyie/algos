"""
Min-Heap Priority Queue based on a linked structure.
"""

import typing

E = typing.TypeVar("E")


class Full(Exception):
    pass


class Empty(Exception):
    pass


class Node(typing.Generic[E]):

    def __init__(self, key: int, element: E, index: int, tree: typing.Optional["MinHeapBinaryTree"] = None) -> None:
        self._key = key
        self._element = element
        self._tree = tree
        self._index = index
        self._parent: typing.Union[Node[E], None] = None
        self._left: typing.Union[Node[E], None] = None
        self._right: typing.Union[Node[E], None] = None
        self._next_sibling: typing.Union[Node[E], None] = None
        self._previous_sibling: typing.Union[Node[E], None] = None

    @property
    def element(self) -> E:
        return self._element

    @property
    def index(self) -> int:
        return self._index

    @property
    def tree(self) -> "MinHeapBinaryTree":
        return self._tree

    @tree.setter
    def tree(self, tree: "MinHeapBinaryTree") -> None:
        if self._tree is not None:
            raise ValueError("Tree is not empty!")
        self._tree = tree

    @property
    def parent(self) -> typing.Union["Node[E]", None]:
        return self._parent

    @parent.setter
    def parent(self, node: typing.Union["Node[E]", None]) -> None:
        self._parent = node

    @property
    def left(self) -> typing.Union["Node[E]", None]:
        return self._left

    @left.setter
    def left(self, node: typing.Union["Node[E]", None]) -> None:
        self._left = node

    @property
    def right(self) -> typing.Union["Node[E]", None]:
        return self._right

    @right.setter
    def right(self, node: typing.Union["Node[E]", None]) -> None:
        self._right = node

    @property
    def next_sibling(self) -> typing.Union["Node[E]", None]:
        return self._next_sibling

    @next_sibling.setter
    def next_sibling(self, node: typing.Union["Node[E]", None]) -> None:
        self._next_sibling = node
        if node is not None:
            node._previous_sibling = self

    @property
    def previous_sibling(self) -> typing.Union["Node[E]", None]:
        return self._previous_sibling

    @previous_sibling.setter
    def previous_sibling(self, node: typing.Union["Node[E]", None]) -> None:
        self._previous_sibling = node
        if node is not None:
            node._next_sibling = self

    def children(self) -> list["Node[E]"]:
        output = []
        if self._left is not None:
            output.append(self._left)
            if self._right is not None:
                output.append(self._right)
        return output

    def has_children(self) -> bool:
        return (self._left is not None) or (self._right is not None)

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}({self._element})"

    def __lt__(self, other: "Node[E]") -> bool:
        return self._key < other._key

    def __gt__(self, other: "Node[E]") -> bool:
        return self._key > other._key


class MinHeapBinaryTree(typing.Generic[E]):
    pass
