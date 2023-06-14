import typing


class Node:

    def __init__(self, data: typing.Any) -> None:
        self._data = data
        self._left = None
        self._right = None
        self._parent = None

    @property
    def parent(self) -> "Node":
        return self._parent

    @parent.setter
    def parent(self, node: "Node") -> None:
        if self._parent is not None:
            raise Exception("Parent already set!")
        self._parent = node

    @property
    def left(self) -> "Node":
        return self._left

    @left.setter
    def left(self, node: "Node") -> None:
        if self._left is not None:
            raise Exception("Left already set!")
        self._left = node
        node.parent = self

    @property
    def right(self) -> "Node":
        return self._right

    @right.setter
    def right(self, node: "Node") -> None:
        if self._right is not None:
            raise Exception("Right already set!")
        self._right = node
        node.parent = self


# =============================================
# EXPRESSION EVALUATION
# =============================================

