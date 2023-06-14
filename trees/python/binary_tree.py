import typing

T = typing.TypeVar("T")


class Node(typing.Generic[T]):

    def __init__(self, data: typing.Optional[T] = None) -> None:
        self._data = data
        self._left = None
        self._right = None
        self._parent = None

    @property
    def data(self) -> T:
        return self._data

    @data.setter
    def data(self, data: T) -> None:
        if self._data is not None:
            raise Exception("Data is already set!")
        self._data = data

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

    def __str__(self) -> str:
        return f"{self.__class__.__name__}({self._data})"


class ExpressionTree:
    """
    e.g: 23+4
    """

    OPERANDS = ["+", "-", "*", "/"]

    def __init__(self, exp: str) -> None:
        self._exp = exp.strip().replace(" ", "")
        self._tree = self.build_tree()

    @staticmethod
    def get_number(n: str, lst: list[str]) -> str:
        if len(lst) <= 0:
            return n
        next_ = lst[0]
        if next_.isdigit():
            return n + ExpressionTree.get_number(next_, lst[1:])
        return n

    @property
    def tree(self) -> Node:
        return self._tree

    def build_tree(self) -> Node:
        expression_list = [*self._exp]

        root = Node()
        current_node = root
        while len(expression_list) > 0:
            try:
                value = expression_list.pop(0)
                if value.isdigit():
                    n = self.get_number(value, expression_list)
                    if current_node is root:
                        node = Node(n)
                        if current_node.left is None:
                            current_node.left = node
                        else:
                            current_node.right = node
                    else:
                        current_node.data = n
                        current_node = current_node.parent
                elif value == "(":
                    if current_node.left is None:
                        current_node.left = Node()
                        current_node = current_node.left
                    else:
                        current_node.right = Node()
                        current_node = current_node.right
                elif value == ")":
                    current_node = current_node.parent
                elif value in self.OPERANDS:
                    current_node.data = value
                    if current_node.left is None:
                        raise ValueError(f"Invalid expression: '{self._exp}'")
                    elif current_node.right is not None:
                        raise ValueError(f"Invalid expression: '{self._exp}'")
                    current_node.right = Node()
                    current_node = current_node.right
                else:
                    raise ValueError(f"Invalid expression: '{self._exp}'")
            except IndexError:
                break

        return root

    def __str__(self) -> str:
        return str(self._tree)


if __name__ == "__main__":
    et = ExpressionTree("((2 * 7) + 8)")
    print(et)
