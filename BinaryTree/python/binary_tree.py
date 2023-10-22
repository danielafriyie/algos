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
        self._layer = 0

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
        if self._left is None:
            raise ValueError("Left is empty!")
        self._right = node

    def children(self) -> list["Node"]:
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


class BinaryTree(typing.Generic[E]):

    def __init__(self) -> None:
        self._root = None
        self._height = 0

    @property
    def depth(self) -> int:
        return len(self.get_layers())

    @property
    def height(self) -> int:
        return self._height

    @property
    def size(self) -> int:
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
        self._height += 1

    def _validate(self, node: Node) -> None:
        if node.tree != self:
            raise ValueError("Node does not belong to this tree!")

    def add_left(self, parent: Node, element: E) -> Node:
        self._validate(parent)
        if parent.left is not None:
            raise ValueError("Parents left is not empty!")
        node = Node(element, self, parent)
        parent.left = node
        self._height += 1
        return node

    def add_right(self, parent: Node, element: E) -> Node:
        self._validate(parent)
        if parent.right is not None:
            raise ValueError("Parents right is not empty!")
        node = Node(element, self, parent)
        parent.right = node
        self._height += 1
        return node

    def is_root(self, node: Node) -> bool:
        return (node == self._root) and (self._root is not None)

    @staticmethod
    def is_leaf(node: Node) -> bool:
        return not node.has_children()

    def get_layers(self) -> list[list[Node]]:
        if self.empty:
            return []
        elif self.is_leaf(self._root):
            return [[self._root]]
        output = [[self._root]]
        children = self._root.children()
        while len(children) > 0:
            output.append(children)
            children_temp = []
            for c in children:
                children_temp.extend(c.children())

            children = children_temp

        return output

    def breath_first_traversal(self) -> list[Node]:
        layers = self.get_layers()
        return [node for lst in layers for node in lst]

    def __iter__(self) -> typing.Iterator[Node]:
        return iter(self.breath_first_traversal())


if __name__ == "__main__":
    btree = BinaryTree()
    root = Node(0, btree)
    btree.root = root
    btree.add_left(root, 1)
    btree.add_right(root, 2)
    btree.add_left(root.left, 3)
    btree.add_right(root.left, 4)
    btree.add_left(root.right, 5)
    btree.add_right(root.right, 6)
    print(btree.depth)
    print(btree.size)
    print(btree)
