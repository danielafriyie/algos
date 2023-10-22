import typing

E = typing.TypeVar("E")


class Node(typing.Generic[E]):

    def __init__(
            self,
            element: E,
            tree: "BinaryTree",
            parent: typing.Optional["Node"] = None,
            left: typing.Optional["Node"] = None,
            right: typing.Optional["Node"] = None,
    ) -> None:
        self._element = element
        self._tree = tree
        self._parent = parent
        self._left = left
        self._right = right

    @property
    def element(self) -> E:
        return self._element

    @property
    def tree(self) -> "BinaryTree":
        return self._tree

    @property
    def parent(self) -> typing.Union["Node", None]:
        return self._parent

    @parent.setter
    def parent(self, node: "Node") -> None:
        self._parent = node

    @property
    def left(self) -> typing.Union["Node", None]:
        return self._left

    @left.setter
    def left(self, node: "Node") -> None:
        self._left = node

    @property
    def right(self) -> typing.Union["Node", None]:
        return self._right

    @right.setter
    def right(self, node: "Node") -> None:
        self._right = node


class BinaryTree(typing.Generic[E]):

    def __init__(self, root: typing.Optional[Node] = None) -> None:
        self._root = root
        self._depth = 0 if root is None else 1
        self._height = 0 if root is None else 1

    @property
    def depth(self) -> int:
        return self._depth

    @property
    def height(self) -> int:
        return self._height

    @property
    def empty(self) -> bool:
        return self._root is None

    @property
    def root(self) -> typing.Union[Node, None]:
        return self._root

    @root.setter
    def root(self, node: None) -> None:
        if self._root is not None:
            raise ValueError("Tree is not empty!")
        self._root = node

    def _validate(self, node: Node) -> None:
        if node.tree != self:
            raise ValueError("Node does not belong to this tree!")

    def add_left(self, parent: Node, element: E) -> Node:
        self._validate(parent)
        if parent.left is not None:
            raise ValueError("Parents left is not empty!")
        node = Node(element, self, parent)
        parent.left = node
        return node

    def add_right(self, parent: Node, element: E) -> Node:
        self._validate(parent)
        if parent.right is not None:
            raise ValueError("Parents right is not empty!")
        node = Node(element, self, parent)
        parent.right = node
        return node

    def remove(self, node: Node) -> None:
        self._validate(node)
        node.parent = None

    def is_root(self, node: Node) -> bool:
        return (node == self._root) and (self._root is not None)

    @staticmethod
    def is_leaf(node: Node) -> bool:
        return (node.left is None) and (node.right is None)

