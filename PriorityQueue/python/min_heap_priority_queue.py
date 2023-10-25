import typing

E = typing.TypeVar("E")


class Node(typing.Generic[E]):

    def __init__(self, key: int, element: E, index: int, tree: typing.Optional["BinaryTree"] = None) -> None:
        self._key = key
        self._element = element
        self._tree = tree
        self._index = index

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
        return self._tree.get_parent(self._index)

    @property
    def left(self) -> typing.Union["Node[E]", None]:
        try:
            return self._tree.get_left(self._index)
        except IndexError:
            return None

    @property
    def right(self) -> typing.Union["Node[E]", None]:
        try:
            return self._tree.get_right(self._index)
        except IndexError:
            return None

    def children(self) -> list["Node[E]"]:
        output = []
        if self.left:
            output.append(self.left)
            if self.right:
                output.append(self.right)
        return output

    def has_children(self) -> bool:
        return len(self.children()) > 0

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}({self._element})"

    def __lt__(self, other: "Node[E]") -> bool:
        return self._key < other._key

    def __gt__(self, other: "Node[E]") -> bool:
        return self._key > other._key


class BinaryTree(typing.Generic[E]):

    def __init__(self, maxsize: int, root: typing.Optional[Node[E]] = None) -> None:
        if maxsize < 1:
            raise ValueError("Maxsize should be greater than or equal to one!")
        if root is not None:
            root.tree = self
        self._maxsize = maxsize
        self._list: list[typing.Union[Node[E], None]] = [None] * maxsize
        self._list[0] = root

    @property
    def depth(self) -> int:
        return self.height(self.root)

    @property
    def maxsize(self) -> int:
        return self._maxsize

    @property
    def empty(self) -> bool:
        return self.root is None

    @property
    def root(self) -> typing.Union[Node[E], None]:
        return self._list[0]

    @root.setter
    def root(self, node: Node[E]) -> None:
        self._list[0] = node

    def _validate(self, node: Node[E]) -> None:
        if node.tree != self:
            raise ValueError("Node does not belong to this tree!")

    def get_parent(self, index: int) -> Node[E]:
        if index == 0:
            return self.root
        return self._list[(index - 1) // 2]

    def add_left(self, index: int, key: int, element: E) -> Node[E]:
        self._validate(self.get_parent(index))
        i = (2 * index) + 1
        node = Node(key, element, i, self)
        self._list[i] = node
        return node

    def get_left(self, index: int) -> Node[E]:
        return self._list[(2 * index) + 1]

    def add_right(self, index: int, key: int, element: E) -> Node[E]:
        self._validate(self.get_parent(index))
        i = (2 * index) + 2
        node = Node(key, element, i, self)
        self._list[i] = node
        return node

    def get_right(self, index: int) -> Node[E]:
        return self._list[(2 * index) + 2]

    def is_root(self, node: Node[E]) -> bool:
        self._validate(node)
        return (node == self.root) and (self.root is not None)

    @staticmethod
    def is_leaf(node: Node[E]) -> bool:
        return not node.has_children()

    @staticmethod
    def height(node: Node[E]) -> int:
        if node is None:
            return 0
        elif not node.has_children():
            return 1
        return 1 + max(BinaryTree.height(node.left), BinaryTree.height(node.right))

    @staticmethod
    def get_layers(node: Node[E]) -> list[list[Node[E]]]:
        if node is None:
            return []
        elif BinaryTree.is_leaf(node):
            return [[node]]
        output = [[node]]
        children = node.children()
        while len(children) > 0:
            output.append(children)
            children_temp = []
            for c in children:
                children_temp.extend(c.children())

            children = children_temp

        return output

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
        results.insert(0, "\n")
        results.append("\n")
        return "\n".join(results)

    @staticmethod
    def visualize_top_to_bottom(node: typing.Optional[Node[E]], depth: int, start: typing.Optional[int] = 0) -> str:

        def _create_index(input_: list[list[Node[E]]]) -> int:
            flattened = [n for lst in input_ for n in lst]
            for idx, n in enumerate(flattened):
                n._index = idx
            return len(flattened)

        def _vis(n: Node[E], d: int, s: int) -> None:
            val = f"{n.element}" if n is not None else " "
            col = d ** 2
            n_str = f"""{" " * col}{" " * s}{val}"""
            results[n._index] = n_str
            if n.left:
                _vis(n.left, d - 1, s)
                if n.right:
                    _vis(n.right, d - 1, col + s)

        layers = BinaryTree.get_layers(node)
        results = [""] * _create_index(layers)
        _vis(node, depth, start)
        output = [""]
        for i in range(depth):
            n = len(layers[i])
            lst = []
            for _ in range(n):
                try:
                    val = results.pop(0)
                    split = val.split(" ")
                    div = len("".join(lst))
                    lst.append(" ".join(split[div:]))
                except IndexError:
                    break
            output.append("".join(lst))
        output.append("\n")
        return "\n\n".join(output)

    def __iter__(self) -> typing.Iterator[Node[E]]:
        return iter(self._list)

    def __str__(self) -> str:
        return self.visualize_top_to_bottom(self.root, self.depth)


if __name__ == "__main__":
    btree = BinaryTree(8, Node(0, 0, 0))
    btree.add_left(0, 1, 1)
    btree.add_right(0, 2, 2)
    btree.add_left(1, 3, 3)
    btree.add_right(1, 4, 4)

    btree.add_left(2, 5, 5)
    btree.add_right(2, 6, 6)

    btree.add_left(3, 7, 7)
    print(btree)
