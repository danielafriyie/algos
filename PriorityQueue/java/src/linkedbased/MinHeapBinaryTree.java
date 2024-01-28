import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeapBinaryTree<E> implements Iterable<Node<E>> {
    private static final int DEFAULT_MAXSIZE = -1;

    private final int maxSize;
    private Node<E> root;
    private int size = 0;
    private Node<E> currentNode;
    private Node<E> bottomNode;
    private Node<E> leftMostNode; // left most node of the current depth
    private Node<E> currentSibling; // current/previous sibling of the current depth

    public MinHeapBinaryTree(int maxSize, Node<E> root) {
        this.maxSize = maxSize;
        if (root != null) {
            root.setTree(this);
            size++;
        }
        this.root = root;
        this.currentNode = root;
        this.bottomNode = root;
    }

    public MinHeapBinaryTree() {
        this(DEFAULT_MAXSIZE, null);
    }

    public int depth() {
        return height(root);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean isFull() {
        return (maxSize >= 0) && (size >= maxSize);
    }

    public boolean isRoot(Node<E> node) throws IllegalArgumentException {
        validate(node);
        return (node == root) && (root != null);
    }

    public Node<E> getRoot() {
        return root;
    }

    public static <T> boolean isLeaf(Node<T> node) {
        return !node.hasChildren();
    }

    public static <T> String visualize(Node<T> node, int level) {
        if (node == null)
            return "";

        return Visualize.visualize(node, level);
    }

    public static <T> int height(Node<T> node) {
        if (node == null) {
            return 0;
        } else if (!node.hasChildren()) {
            return 1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public static <T> List<List<Node<T>>> getLayers(Node<T> node) {
        if (node == null) {
            return List.of();
        } else if (isLeaf(node)) {
            return List.of(List.of(node));
        }

        List<List<Node<T>>> layers = new ArrayList<>();
        layers.add(List.of(node));
        List<Node<T>> children = node.children();

        while (!children.isEmpty()) {
            layers.add(children);
            List<Node<T>> childrenTemp = new ArrayList<>();
            children.forEach((Node<T> child) -> {
                childrenTemp.addAll(child.children());
            });

            children = childrenTemp;
        }

        return layers;
    }

    @SafeVarargs
    private void validate(Node<E>... nodes) throws IllegalArgumentException {
        for (Node<E> n : nodes) {
            if (n == null) {
                throw new IllegalArgumentException("Node cannot be null!");
            } else if (n.getTree() != this) {
                throw new IllegalArgumentException("Node does not belong to this tree!");
            }
        }
    }

    private void checkSize(int n) throws Full {
        if ((maxSize > 0) && ((size + n) > maxSize)) {
            throw new Full();
        }
    }

    /**
     * Swaps the parent's position with the child
     */
    private void swap(Node<E> parent, Node<E> child) throws IllegalArgumentException {
        validate(parent, child);

        // Gets the parent's attribute/properties
        Node<E> grandParent = parent.getParent();
        Node<E> parentLeft = parent.getLeft();
        Node<E> parentRight = parent.getRight();
        Node<E> parentNextSibling = parent.getNextSibling();
        Node<E> parentPreviousSibling = parent.getPreviousSibling();

        // Gets the child's attribute/properties
        Node<E> childLeft = child.getLeft();
        Node<E> childRight = child.getRight();
        Node<E> childNextSibling = child.getNextSibling();
        Node<E> childPreviousSibling = child.getPreviousSibling();

        // Swaps children
        // If child is on its parent's left, then its parent will become its left
        // then its parent's right (child's sibling) will become its right
        // and vice versa
        parent.setLeft(childLeft);
        parent.setRight(childRight);
        if (child == parentLeft) {
            child.setLeft(parent);
            child.setRight(parentRight);
        } else if (child == parentRight) {
            child.setRight(parent);
            child.setLeft(parentLeft);
        } else {
            child.setLeft(parentLeft);
            child.setRight(parentRight);
        }

        // Swaps the parent's position with the child
        child.setParent(grandParent);
        if (grandParent != null) {
            if (parent == grandParent.getLeft()) {
                grandParent.setLeft(child);
            } else {
                grandParent.setRight(child);
            }
        }

        // Swap siblings
        parent.setNextSibling(childNextSibling);
        parent.setPreviousSibling(childPreviousSibling);
        child.setNextSibling(parentNextSibling);
        child.setPreviousSibling(parentPreviousSibling);

        // Check and set the left most node, current sibling, current node, and bottom node
        if (leftMostNode == child) {
            leftMostNode = parent;
        }

        if (currentSibling == child) {
            currentSibling = parent;
        }

        if (bottomNode == child) {
            bottomNode = parent;
        }

        if (currentNode == parent) {
            currentNode = child;
        } else if (currentNode == child) {
            currentNode = parent;
        }

        if (isRoot(parent)) {
            root = child;
        }
    }

    private void upHeap(Node<E> node) {
        if (node == null)
            return;
        Node<E> parent = node.getParent();
        if (parent == null)
            return;
        if (parent.compareTo(node) <= 0)
            return;
        swap(parent, node);
        upHeap(node);
    }

    private void downHeap(Node<E> node) {
        Node<E> left = node.getLeft();
        Node<E> right = node.getRight();
        if (left != null) {
            Node<E> childToSwap = left;
            if ((right != null) && (right.compareTo(left) < 0)) {
                childToSwap = right;
            }
            swap(node, childToSwap);
            downHeap(node);
        }
    }

    /**
     * Get the parent to use to insert/append new node.
     * Strategy used:
     * Check if current node (could be root) is full (i.e: both its left and right is set).
     * If full, then get its next sibling then return it.
     * If next sibling is `null` that means, the current node is the last sibling
     * So move down a depth (return the left most node).
     */
    private Node<E> getParent() {
        if (!currentNode.isFull())
            return currentNode;
        Node<E> nextSibling = currentNode.getNextSibling();
        if (nextSibling != null) {
            currentNode = nextSibling;
            return nextSibling;
        }

        currentNode = leftMostNode;

        // set current sibling and left most node to `null` to indicate an end of a depth.
        currentSibling = null;
        leftMostNode = null;

        return currentNode;
    }

    private Node<E> addLeft(Node<E> parent, int key, E element) throws Full {
        checkSize(1);
        Node<E> node = new Node<>(key, element, this);
        parent.setLeft(node);
        size++;
        return node;
    }

    private Node<E> addRight(Node<E> parent, int key, E element) throws Full {
        checkSize(1);
        Node<E> node = new Node<>(key, element, this);
        parent.setRight(node);
        size++;
        return node;
    }

    /**
     * Returns the last/bottom node in the tree.
     * Eg:
     * 0
     * 1    2
     * 3  4  5  6
     * Returns 6 (the last/bottom node)
     *
     * @return Node<E>
     */
    private Node<E> getBottomNode() {
        Node<E> right = bottomNode.getRight();
        if (right != null)
            return right;
        Node<E> left = bottomNode.getLeft();
        if (left != null)
            return left;
        return bottomNode;
    }

    public Node<E> insert(int key, E element) throws Full {
        checkSize(1);

        if (isEmpty()) {
            Node<E> node = new Node<>(key, element, this);
            root = node;
            currentNode = node;
            bottomNode = node;
            size++;
            return node;
        }

        Node<E> node;
        if (size == 1) {
            // if size == 1, that means the current node (root) doesn't have any children
            // so set the left most node and current sibling to the root's left
            node = addLeft(root, key, element);
            leftMostNode = node;
        } else {
            Node<E> parent = getParent();
            if (parent.getLeft() == null) {
                node = addLeft(parent, key, element);
                // If the left most node is `null`, then we are begining
                // on a new depth, so set the left most node to the new node.
                if (leftMostNode == null)
                    leftMostNode = node;
            } else {
                node = addRight(parent, key, element);
            }

            node.setPreviousSibling(currentSibling);
        }

        currentSibling = node;
        bottomNode = node;
        upHeap(node);

        return node;
    }

    public Node<E> pop() throws Empty {
        if (isEmpty())
            throw new Empty();

        Node<E> rootNode = root;
        if (size == 1) {
            root = null;
            size = 0;
            rootNode.setTree(null);
            return rootNode;
        }

        Node<E> bottomNode = getBottomNode();

        // Set the bottom's node position on its parent to `null`
        // i.e: prevents recursion error
        Node<E> bottomNodeParent = bottomNode.getParent();
        if (bottomNodeParent != null) {
            if (bottomNodeParent.getLeft() == bottomNode) {
                bottomNodeParent.setLeft(bottomNodeParent.getRight());
            }
            bottomNodeParent.setRight(null);
        }

        swap(rootNode, bottomNode);

        // clears the root node (set its reference pointers to `null`)
        rootNode.setTree(null);
        rootNode.setParent(null);
        rootNode.setLeft(null);
        rootNode.setRight(null);
        rootNode.setNextSibling(null);
        Node<E> prevSibling = rootNode.getPreviousSibling();
        this.bottomNode = prevSibling;
        if (currentNode == rootNode)
            currentNode = prevSibling;
        if (leftMostNode == rootNode)
            leftMostNode = null;
        if (currentSibling == rootNode)
            currentNode = prevSibling;
        if (prevSibling != null)
            prevSibling.setNextSibling(null);
        rootNode.setPreviousSibling(null);

        downHeap(bottomNode);
        size--;
        return rootNode;
    }

    public int length() {
        return getSize();
    }

    @Override
    public String toString() {
        return visualize(root, 0);
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return new MinHeapBinaryTreeIterator(root);
    }

    public static class Full extends Exception {
    }

    public static class Empty extends Exception {
    }

    public static class Visualize {

        private static <T> List<String> apply(Node<T> n, int lvl) {
            List<String> output = new ArrayList<>();
            if (n.getRight() != null) {
                output.addAll(apply(n.getRight(), lvl + 2));
            }
            output.add(" ".repeat(4).repeat(lvl) + "-> " + n.getElement());
            if (n.getLeft() != null) {
                output.addAll(apply(n.getLeft(), lvl + 2));
            }

            return output;
        }

        public static <T> String visualize(Node<T> node, int level) {
            List<String> results = apply(node, level);
            results.add(0, "\n");
            results.add("\n");
            return String.join("\n", results);
        }
    }

    public class MinHeapBinaryTreeIterator implements Iterator<Node<E>> {
        private Node<E> next;
        private Node<E> nextDepthLeftMostNode; // next depth left most node for iteration in breath first fashion

        private MinHeapBinaryTreeIterator(Node<E> node) {
            this.next = node;
            if (node != null)
                this.nextDepthLeftMostNode = node.getLeft();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node<E> next() {
            if (next == null) {
                throw new NoSuchElementException();
            }

            Node<E> node = this.next;
            Node<E> sibling = node.getNextSibling();
            if (sibling != null) {
                this.next = sibling;
            } else {
                this.next = nextDepthLeftMostNode;
                if (nextDepthLeftMostNode != null) {
                    this.nextDepthLeftMostNode = this.nextDepthLeftMostNode.getLeft();
                }
            }

            return node;
        }
    }
}
