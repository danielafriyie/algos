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
        self._index = -1  # only computed when generating tree visualization

    @property
    def element(self) -> E:
        return self._element

    @property
    def index(self) -> int:
        return self._index

    @index.setter
    def index(self, i: int) -> None:
        self._index = i

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


class BinaryExpressionTree:

    def __init__(self, expression: str) -> None:
        self._expression = expression.replace(" ", "")
        self._root: typing.Union[Node[str], None] = None

    @property
    def depth(self) -> int:
        return len(self.get_layers(self._root))

    @property
    def height(self) -> int:
        layers = self.get_layers(self._root)
        return sum(len(lst) for lst in layers)

    @property
    def size(self) -> int:
        return self.height

    @property
    def empty(self) -> bool:
        return self._root is None

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
        layers = self.get_layers(self._root)
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
        results.insert(0, "\n")
        results.append("\n")
        return "\n".join(results)

    @staticmethod
    def visualize_top_to_bottom(node: typing.Optional[Node[E]], depth: int, start: typing.Optional[int] = 0) -> str:

        def _create_index(input_: list[list[Node[E]]]) -> int:
            flattened = [n for lst in input_ for n in lst]
            for idx, n in enumerate(flattened):
                n.index = idx
            return len(flattened)

        def _vis(n: Node[E], d: int, s: int) -> None:
            val = f"{n.element}" if n is not None else " "
            col = d ** 2
            n_str = f"""{" " * col}{" " * s}{val}"""
            results[n.index] = n_str
            if n.left:
                _vis(n.left, d - 1, s)
                if n.right:
                    _vis(n.right, d - 1, col + s)

        layers = BinaryExpressionTree.get_layers(node)
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

    @staticmethod
    def is_leaf(node: Node[E]) -> bool:
        return not node.has_children()

    @staticmethod
    def get_layers(node: Node[E]) -> list[list[Node[E]]]:
        if node is None:
            return []
        elif BinaryExpressionTree.is_leaf(node):
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

    def __iter__(self) -> typing.Iterator[Node[E]]:
        return iter(self.breath_first_traversal())

    def __str__(self) -> str:
        return self.visualize_top_to_bottom(self._root, self.depth)
