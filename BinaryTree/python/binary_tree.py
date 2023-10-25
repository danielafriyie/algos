import typing

E = typing.TypeVar("E")


class Node(typing.Generic[E]):

    def __init__(
            self,
            element: E,
            tree: typing.Optional["BinaryTree"] = None,
            parent: typing.Optional["Node[E]"] = None,
            left: typing.Optional["Node[E]"] = None,
            right: typing.Optional["Node[E]"] = None,
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

    @tree.setter
    def tree(self, tree: "BinaryTree") -> None:
        if self._tree:
            raise ValueError("Tree is not empty!")
        self._tree = tree

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


class BinaryTree(typing.Generic[E]):

    def __init__(self, root: typing.Optional[Node[E]] = None) -> None:
        if root is not None:
            root.tree = self
        self._root = root

    @property
    def depth(self) -> int:
        return len(self.get_layers())

    @property
    def height(self) -> int:
        layers = self.get_layers()
        return sum(len(lst) for lst in layers)

    @property
    def size(self) -> int:
        return self.height

    @property
    def empty(self) -> bool:
        return self._root is None

    @property
    def root(self) -> typing.Union[Node[E], None]:
        return self._root

    @root.setter
    def root(self, node: None) -> None:
        if self._root is not None:
            raise ValueError("Tree is not empty!")
        self._root = node

    def _validate(self, node: Node[E]) -> None:
        if node.tree != self:
            raise ValueError("Node does not belong to this tree!")

    def add_left(self, parent: Node[E], element: E) -> Node[E]:
        self._validate(parent)
        if parent.left is not None:
            raise ValueError("Parents left is not empty!")
        node = Node(element, self, parent)
        parent.left = node
        return node

    def add_right(self, parent: Node[E], element: E) -> Node[E]:
        self._validate(parent)
        if parent.right is not None:
            raise ValueError("Parents right is not empty!")
        node = Node(element, self, parent)
        parent.right = node
        return node

    def is_root(self, node: Node[E]) -> bool:
        return (node == self._root) and (self._root is not None)

    @staticmethod
    def is_leaf(node: Node[E]) -> bool:
        return not node.has_children()

    @staticmethod
    def node_height(node: Node[E]) -> int:
        if node is None:
            return 0
        elif not node.has_children():
            return 1
        return 1 + max(BinaryTree.node_height(node.left), BinaryTree.node_height(node.right))

    def get_layers(self) -> list[list[Node[E]]]:
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

    def inorder_traversal(self, node: typing.Optional[Node[E]] = None) -> list[Node[E]]:
        if self.empty:
            return []
        if node is None:
            node = self._root
        output = []
        if node.left:
            output.extend(self.postorder_traversal(node.left))
        output.append(node)
        if node.right:
            output.extend(self.postorder_traversal(node.right))
        return output

    def preorder_traversal(self, node: typing.Optional[Node[E]] = None) -> list[Node[E]]:
        if self.empty:
            return []
        if node is None:
            node = self._root
        output = [node]

        children = node.children()
        for child in children:
            output.extend(self.preorder_traversal(child))

        return output

    def postorder_traversal(self, node: typing.Optional[Node[E]] = None) -> list[Node[E]]:
        if self.empty:
            return []
        if node is None:
            node = self._root
        if self.is_leaf(node):
            return [node]
        output = []
        if node.left:
            output.extend(self.postorder_traversal(node.left))
        if node.right:
            output.extend(self.postorder_traversal(node.right))
        output.append(node)
        return output

    def breath_first_traversal(self) -> list[Node[E]]:
        layers = self.get_layers()
        return [node for lst in layers for node in lst]

    @staticmethod
    def visualize_left_to_right(node: typing.Optional[Node[E]], level: typing.Optional[int]) -> str:

        def _vis(n: Node[E], lvl: int) -> list[str]:
            output = []
            if n.right:
                output.extend(_vis(n.right, lvl + 2))
            output.append(f"""{((" " * 4) * lvl)}-> {n.element}""")
            if n.left:
                output.extend(_vis(n.left, lvl + 2))
            return output

        if node is None:
            return ""
        results = _vis(node, level)
        return "\n".join(results)

    @staticmethod
    def visualize_top_to_bottom(node: typing.Optional[Node[E]], depth: int, start: typing.Optional[int] = 0) -> str:

        def _vis(n: Node[E], d: int, s: int) -> list[str]:
            val = f"{n.element}" if n is not None else " "
            col = d ** 2
            output = [(" " * col) + (" " * s), val]
            output.extend(_vis(n.left, d - 1, col ))

            return output

    def __iter__(self) -> typing.Iterator[Node[E]]:
        return iter(self.breath_first_traversal())

    def __str__(self) -> str:
        return self.visualize_left_to_right(self._root, 0)


if __name__ == "__main__":
    btree: BinaryTree[int] = BinaryTree(Node(0))
    btree.add_left(btree.root, 1)
    btree.add_right(btree.root, 2)

    btree.add_left(btree.root.left, 3)
    btree.add_right(btree.root.left, 4)

    btree.add_left(btree.root.right, 5)
    btree.add_right(btree.root.right, 6)

    btree.add_left(btree.root.left.left, 7)
    print(btree.depth)
    print(btree.size)
    print(btree.inorder_traversal())
    print(btree.preorder_traversal())
    print(btree.postorder_traversal())
    print(btree.breath_first_traversal())
    print()
    print(btree)
