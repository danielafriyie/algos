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

    def __init__(self, key: int, element: E, tree: typing.Optional["MinHeapBinaryTree"] = None) -> None:
        self._key = key
        self._element = element
        self._tree = tree
        self._parent: typing.Union[Node[E], None] = None
        self._left: typing.Union[Node[E], None] = None
        self._right: typing.Union[Node[E], None] = None
        self._next_sibling: typing.Union[Node[E], None] = None
        self._previous_sibling: typing.Union[Node[E], None] = None

    @property
    def element(self) -> E:
        return self._element

    @property
    def tree(self) -> "MinHeapBinaryTree":
        return self._tree

    @tree.setter
    def tree(self, tree: typing.Union["MinHeapBinaryTree", None]) -> None:
        if (tree is not None) and (self._tree is not None):
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
        if node is not None:
            node._parent = self

    @property
    def right(self) -> typing.Union["Node[E]", None]:
        return self._right

    @right.setter
    def right(self, node: typing.Union["Node[E]", None]) -> None:
        self._right = node
        if node is not None:
            node._parent = self

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

    def is_full(self) -> bool:
        return (self._left is not None) and (self._right is not None)

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

    def __init__(self, maxsize: typing.Optional[int] = -1, root: typing.Optional[Node[E]] = None) -> None:
        if root is not None:
            root.tree = self
        self._root = root
        self._maxsize = maxsize
        self._size = 0 if root is None else 1
        self._current_node: typing.Union[Node[E], None] = root
        self._left_most_node: typing.Union[Node[E], None] = None  # left most node of the current layer
        self._current_sibling: typing.Union[Node[E], None] = None  # current/previous sibling of the current layer

    @property
    def depth(self) -> int:
        return self.height(self._root)

    @property
    def size(self) -> int:
        return self._size

    @property
    def maxsize(self) -> int:
        return self._maxsize

    @property
    def empty(self) -> bool:
        return self._root is None

    @property
    def is_full(self) -> bool:
        return False if self._maxsize < 0 else self._size >= self._maxsize

    @property
    def root(self) -> typing.Union[Node[E], None]:
        return self._root

    @root.setter
    def root(self, node: Node[E]) -> None:
        self._root = node

    @staticmethod
    def is_leaf(node: Node[E]) -> bool:
        return not node.has_children()

    @staticmethod
    def visualize_left_to_right(node: typing.Optional[Node[E]], level: typing.Optional[int]) -> str:
        if node is None:
            return ""

        def _vis(n: Node[E], lvl: int) -> list[str]:
            output = []
            if n.right:
                output.extend(_vis(n.right, lvl + 2))
            output.append(f"""{((" " * 4) * lvl)}-> {n.element}""")
            if n.left:
                output.extend(_vis(n.left, lvl + 2))
            return output

        results = _vis(node, level)
        results.insert(0, "\n")
        results.append("\n")
        return "\n".join(results)

    @classmethod
    def height(cls, node: Node[E]) -> int:
        if node is None:
            return 0
        elif not node.has_children():
            return 1
        return 1 + max(cls.height(node.left), cls.height(node.right))

    @classmethod
    def get_layers(cls, node: Node[E]) -> list[list[Node[E]]]:
        if node is None:
            return []
        elif cls.is_leaf(node):
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

    def _validate(self, *nodes: Node[E]) -> None:
        for node in nodes:
            if node is None:
                raise TypeError("Node cannot be None!")
            elif node.tree != self:
                raise ValueError("Node does not belong to this tree!")

    def _check_size(self, n: int) -> None:
        if (self._maxsize > 0) and ((self._size + n) > self._maxsize):
            raise Full

    def _swap(self, parent: Node[E], child: Node[E]) -> None:
        """Swaps parent's position with the child's"""
        self._validate(parent, child)

        # get parent's attributes/properties
        grand_parent = parent.parent
        parent_left, parent_right = parent.left, parent.right
        parent_next_sibling, parent_previous_sibling = parent.next_sibling, parent.previous_sibling

        # get child's attributes/properties
        child_left, child_right = child.left, child.right
        child_next_sibling, child_previous_sibling = child.next_sibling, child.previous_sibling

        # Swap children
        # if child is on its parent's left, then its parent will become its left
        # then its parent's right (child's sibling) will become its right
        # and vice versa
        parent.left = child_left
        parent.right = child_right
        if child == parent_left:
            child.left = parent
            child.right = parent_right
        elif child == parent_right:
            child.right = parent
            child.left = parent_left

        # set the child's parent
        child.parent = grand_parent

        # Swap siblings
        parent.next_sibling = child_next_sibling
        parent.previous_sibling = child_previous_sibling
        child.next_sibling = parent_next_sibling
        child.previous_sibling = parent_previous_sibling

        # Check and set the left most node and the current sibling
        if self._left_most_node == child:
            self._left_most_node = parent
        if self._current_sibling == child:
            self._current_sibling = parent

        if self.is_root(parent):
            self._root = child

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
        if left is not None:
            child_to_swap = left
            if (right is not None) and (right < left):
                child_to_swap = right
            self._swap(node, child_to_swap)
            self._down_heap(node)

    def add_left(self, parent: Node[E], key: int, element: E) -> Node[E]:
        self._check_size(1)
        parent.left = Node(key, element, self)
        self._size += 1
        return parent.left

    def add_right(self, parent: Node[E], key: int, element: E) -> Node[E]:
        self._check_size(1)
        parent.right = Node(key, element, self)
        self._size += 1
        return parent.right

    def is_root(self, node: Node[E]) -> bool:
        self._validate(node)
        return (node == self._root) and (self._root is not None)

    def get_parent(self) -> Node[E]:
        """
        Get the parent to use to insert/append new node.
        Strategy used:
            Check if current node (could be root) is full (i.e: both its left and right is set).
            If full, then get its next sibling then return it.
            If next sibling is `None` that means, the current node is the last sibling
            So move down a layer (return the left most node).
        """
        if not self._current_node.is_full():
            return self._current_node
        next_sibling = self._current_node.next_sibling
        if next_sibling is not None:
            return next_sibling

        self._current_node = self._left_most_node

        # set current sibling and left most node to `None` to indicate an end of a layer.
        self._current_sibling = None
        self._left_most_node = None

        return self._current_node

    def insert(self, key: int, element: E) -> Node[E]:
        self._check_size(1)

        if self.empty:
            node = Node(key, element, self)
            self._root = node
            self._current_node = node
            self._size += 1
            return node

        if self._size == 1:
            # if size == 1, that means the current node (root) doesn't have any children
            # so set the left most node and current sibling to the root's left
            node = self.add_left(self._root, key, element)
            self._left_most_node = node
            self._current_sibling = node
        else:
            parent = self.get_parent()
            if not parent.left:
                node = self.add_left(parent, key, element)
                # If the left most node is `None`, then we are begining
                # on a new layer, so set the left most node to the new node.
                if self._left_most_node is None:
                    self._left_most_node = node
            else:
                node = self.add_right(parent, key, element)

            node.previous_sibling = self._current_sibling
            self._current_sibling = node

        self._up_heap(node)
        return node

    def get_bottom_node(self) -> Node[E]:
        """
        Returns the last/bottom node in the tree.
        Eg:
                  0
               1    2
             3  4  5  6

        Returns 6 (the last/bottom node)
        """
        left, right = self._current_node.left, self._current_node.right
        if right is not None:
            return right
        return left

    def pop(self) -> typing.Union[Node[E], None]:
        if self.empty:
            raise Empty

        root = self._root
        if self._size == 1:
            self._root = None
            self._size = 0
            root.tree = None
            return root

        self._swap(root, self.get_bottom_node())
        root.tree = None
        self._down_heap(self._root)
        self._size -= 1
        return root

    def __str__(self) -> str:
        return self.visualize_left_to_right(self._root, 0)


class PriorityQueue(typing.Generic[E]):

    def __init__(self, maxsize: typing.Optional[int] = -1) -> None:
        self._tree: MinHeapBinaryTree[E] = MinHeapBinaryTree(maxsize)

    @property
    def size(self) -> int:
        return self._tree.size

    @property
    def empty(self) -> bool:
        return self._tree.empty

    def insert(self, key: int, element: E) -> Node[E]:
        return self._tree.insert(key, element)

    def min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        return self._tree.root

    def remove_min(self) -> typing.Union[Node[E], None]:
        if self.empty:
            return None
        return self._tree.pop()

    def __str__(self) -> str:
        return str(self._tree)


if __name__ == "__main__":
    queue: PriorityQueue[int] = PriorityQueue()
    queue.insert(0, 0)
    queue.insert(1, 1)
    queue.insert(5, 5)
    queue.insert(4, 4)
    queue.insert(10, 10)
    queue.insert(3, 3)
    print(queue.size)
    print(queue.min())
    print(queue)
