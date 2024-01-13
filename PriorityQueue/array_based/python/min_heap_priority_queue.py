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


class MinHeapBinaryTree(typing.Generic[E]):

    def __init__(self, maxsize: int, root: typing.Optional[Node[E]] = None) -> None:
        if maxsize < 1:
            raise ValueError("Maxsize should be greater than or equal to one!")
        if root is not None:
            root.tree = self
        self._maxsize = maxsize
        self._list: list[typing.Union[Node[E], None]] = [None] * maxsize
        self._list[0] = root
        self._size = 0 if root is None else 1

    @property
    def depth(self) -> int:
        return self.height(self.root)

    @property
    def size(self) -> int:
        return self._size

    @property
    def maxsize(self) -> int:
        return self._maxsize

    @property
    def empty(self) -> bool:
        return self.root is None

    @property
    def is_full(self) -> bool:
        return self._size >= self._maxsize

    @property
    def root(self) -> typing.Union[Node[E], None]:
        return self._list[0]

    @root.setter
    def root(self, node: Node[E]) -> None:
        self._list[0] = node
        self._size = 1

    def _validate(self, node: Node[E]) -> None:
        if node.tree != self:
            raise ValueError("Node does not belong to this tree!")

    def _check_size(self, n: int) -> None:
        if (self._size + n) > self._maxsize:
            raise Full

    def get_parent(self, index: int) -> typing.Union[Node[E], None]:
        if index == 0:
            return None
        return self._list[(index - 1) // 2]

    def add_left(self, index: int, key: int, element: E) -> Node[E]:
        self._check_size(1)
        i = (2 * index) + 1
        node = Node(key, element, i, self)
        self._list[i] = node
        self._size += 1
        return node

    def get_left(self, index: int) -> Node[E]:
        return self._list[(2 * index) + 1]

    def add_right(self, index: int, key: int, element: E) -> Node[E]:
        self._check_size(1)
        i = (2 * index) + 2
        node = Node(key, element, i, self)
        self._list[i] = node
        self._size += 1
        return node

    def get_right(self, index: int) -> Node[E]:
        return self._list[(2 * index) + 2]

    def is_root(self, node: Node[E]) -> bool:
        self._validate(node)
        return (node == self.root) and (self.root is not None)

    def _swap(self, parent: Node[E], child: Node[E]) -> None:
        """Swaps parent position with the child's"""
        pindex, cindex = parent.index, child.index
        self._list[pindex] = child
        self._list[cindex] = parent
        parent._index = cindex
        child._index = pindex

    def _up_heap(self, node: Node[E]) -> None:
        if node is None:
            return
        parent = node.parent
        if parent is None:
            return
        if parent < node:
            return
        self._swap(parent, node)
        self._up_heap(node)

    def _down_heap(self, node: Node[E]) -> None:
        left, right = node.left, node.right
        if left:
            child_to_swap = left
            if right:
                if right < left:
                    child_to_swap = right
            self._swap(node, child_to_swap)
            self._down_heap(node)

    def insert(self, key: int, element: E) -> Node[E]:
        self._check_size(1)

        if self.empty:
            node = Node(key, element, 0, self)
            self.root = node
            return node

        parent = self.get_parent(self._size)
        if not parent.left:
            node = self.add_left(parent.index, key, element)
        else:
            node = self.add_right(parent.index, key, element)

        self._up_heap(node)
        return node

    def pop(self) -> typing.Union[Node[E], None]:
        if self.empty:
            raise Empty
        root = self.root

        self._swap(root, self._list[self._size - 1])
        self._list[root.index] = None
        self._down_heap(self._list[0])
        self._size -= 1
        return root

    @staticmethod
    def is_leaf(node: Node[E]) -> bool:
        return not node.has_children()

    @staticmethod
    def height(node: Node[E]) -> int:
        if node is None:
            return 0
        elif not node.has_children():
            return 1
        return 1 + max(MinHeapBinaryTree.height(node.left), MinHeapBinaryTree.height(node.right))

    @staticmethod
    def get_layers(node: Node[E]) -> list[list[Node[E]]]:
        if node is None:
            return []
        elif MinHeapBinaryTree.is_leaf(node):
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

        layers = MinHeapBinaryTree.get_layers(node)
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


class PriorityQueue(typing.Generic[E]):

    def __init__(self, maxsize: int) -> None:
        self._list: MinHeapBinaryTree[E] = MinHeapBinaryTree(maxsize)

    @property
    def size(self) -> int:
        return self._list.size

    @property
    def empty(self) -> bool:
        return self._list.empty

    def insert(self, key: int, element: E) -> Node[E]:
        return self._list.insert(key, element)

    def min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        return self._list.root

    def remove_min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        return self._list.pop()

    def __str__(self) -> str:
        return str(self._list)


if __name__ == "__main__":
    queue: PriorityQueue[int] = PriorityQueue(6)
    queue.insert(0, 324)
    queue.insert(1, 53)
    queue.insert(5, 23)
    queue.insert(4, 32)
    queue.insert(10, 2)
    queue.insert(3, 23234)
    print(queue.size)
    print(queue.min())
    print(queue)
