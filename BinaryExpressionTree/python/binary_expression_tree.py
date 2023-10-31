import typing

E = typing.TypeVar("E")


class Node(typing.Generic[E]):

    def __init__(
            self,
            element: E,
            parent: typing.Optional["Node[E]"] = None,
            left: typing.Optional["Node[E]"] = None,
            right: typing.Optional["Node[E]"] = None,
    ) -> None:
        self._element = element
        self._parent = parent
        self._left = left
        self._right = right

    @property
    def element(self) -> E:
        return self._element

    @property
    def parent(self) -> typing.Union["Node[E]", None]:
        return self._parent

    @parent.setter
    def parent(self, node: "Node[E]") -> None:
        if self._parent:
            raise ValueError("Parent is not empty!")
        self._parent = node

    @property
    def left(self) -> typing.Union["Node[E]", None]:
        return self._left

    @left.setter
    def left(self, node: "Node[E]") -> None:
        if self._left:
            raise ValueError("Left is not empty!")
        self._left = node

    @property
    def right(self) -> typing.Union["Node[E]", None]:
        return self._right

    @right.setter
    def right(self, node: "Node[E]") -> None:
        if self._right:
            raise ValueError("Right is not empty!")
        if self._left is None:
            raise ValueError("Left is empty!")
        self._right = node

    def children(self) -> list["Node[E]"]:
        output = []
        if self._left:
            output.append(self._left)
            if self._right:
                output.append(self._right)
        return output

    def has_children(self) -> bool:
        return len(self.children()) > 0

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}({self._element})"
