package arraybased;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MinHeapPriorityQueue<E> {
    private final MinHeapBinaryTree<E> tree;

    public MinHeapPriorityQueue(int maxSize) {
        this.tree = new MinHeapBinaryTree<>(maxSize);
    }

    public int size() {
        return tree.size();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public Node<E> insert(int key, E element) throws Full {
        return tree.insert(key, element);
    }

    public Node<E> min() {
        if (isEmpty())
            return null;
        return tree.getRoot();
    }

    public Node<E> removeMin() throws Empty {
        if (isEmpty())
            return null;
        return tree.pop();
    }

    public void visualize() {
        tree.visualize();
    }

    public static void main(String[] args) throws Empty, Full {
        MinHeapPriorityQueue<Integer> queue = new MinHeapPriorityQueue<>(12);
        for (int i : new int[] {14, 9, 29, 4, 26, 19, 8, 15, 5, 17, 12, 18}) {
            queue.insert(i, i);
        }
        System.out.println();
        queue.visualize();
        System.out.println();
        System.out.println(queue.removeMin());
        System.out.println();
        queue.visualize();
        System.out.println();
    }

    public static class Full extends Exception {
    }

    public static class Empty extends Exception {
    }

    public static class Node<E> implements Comparable<Node<E>> {
        private final int key;
        private final E element;
        private int index;
        private MinHeapBinaryTree<E> tree;

        private Node(int key, E element, int index, MinHeapBinaryTree<E> tree) {
            this.key = key;
            this.element = element;
            this.index = index;
            this.tree = tree;
        }

        private Node(int key, E element, int index) {
            this(key, element, index, null);
        }

        public int getKey() {
            return key;
        }

        public E getElement() {
            return element;
        }

        public int getIndex() {
            return index;
        }

        private void setIndex(int index) {
            this.index = index;
        }

        public MinHeapBinaryTree<E> getTree() {
            return tree;
        }

        private void setTree(MinHeapBinaryTree<E> tree) {
            this.tree = tree;
        }

        public Node<E> getParent() {
            return tree.getParent(index);
        }

        public Node<E> getLeft() {
            try {
                return tree.getLeft(index);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        public Node<E> getRight() {
            try {
                return tree.getRight(index);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        public List<Node<E>> children() {
            List<Node<E>> output = new ArrayList<>();
            if (getLeft() != null) {
                output.add(getLeft());
                if (getRight() != null) {
                    output.add(getRight());
                }
            }
            return output;
        }

        public boolean hasChildren() {
            return !children().isEmpty();
        }

        @Override
        public int compareTo(Node<E> other) {
            return Integer.compare(key, other.key);
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", getClass().getName(), element);
        }
    }

    public static class MinHeapBinaryTree<E> {
        private final int maxSize;
        private final List<Node<E>> list;
        private int size;

        private MinHeapBinaryTree(int maxSize, Node<E> root) {
            if (maxSize < 1)
                throw new IllegalArgumentException("Max size should be greater than or equal to 1!");
            this.maxSize = maxSize;
            this.list = new ArrayList<>(Collections.nCopies(maxSize, null));
            if (root != null) {
                root.setTree(this);
                this.size = 1;
            } else {
                this.size = 0;
            }
            list.set(0, root);
        }

        private MinHeapBinaryTree(int maxSize) {
            this(maxSize, null);
        }

        public int depth() {
            return height(getRoot());
        }

        public int size() {
            return size;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public boolean isEmpty() {
            return size < 1;
        }

        public boolean isFull() {
            return size >= maxSize;
        }

        public Node<E> getRoot() {
            return list.get(0);
        }

        public void setRoot(Node<E> node) {
            list.set(0, node);
            this.size = 1;
        }

        private void validate(Node<E> node) {
            if (node.getTree() != this)
                throw new IllegalArgumentException("Node does not belong to this tree!");
        }

        @SuppressWarnings("all")
        private void checkSize(int n) throws Full {
            if ((size + n) > maxSize)
                throw new Full();
        }

        public Node<E> getParent(int index) {
            if (index == 0)
                return null;
            return list.get((index - 1) / 2);
        }

        public Node<E> addLeft(int index, int key, E element) throws Full {
            checkSize(1);
            int i = (2 * index) + 1;
            Node<E> node = new Node<>(key, element, i, this);
            list.set(i, node);
            size++;
            return node;
        }

        public Node<E> getLeft(int index) {
            return list.get((2 * index) + 1);
        }

        public Node<E> addRight(int index, int key, E element) throws Full {
            checkSize(1);
            int i = (2 * index) + 2;
            Node<E> node = new Node<>(key, element, i, this);
            list.set(i, node);
            size++;
            return node;
        }

        public Node<E> getRight(int index) {
            return list.get((2 * index) + 2);
        }

        public boolean isRoot(Node<E> node) {
            validate(node);
            return (node == getRoot()) && (getRoot() != null);
        }

        private void swap(Node<E> parent, Node<E> child) {
            int pindex = parent.getIndex();
            int cindex = child.getIndex();
            list.set(pindex, child);
            list.set(cindex, parent);
            parent.index = cindex;
            child.index = pindex;
        }

        private void upHeap(Node<E> node) {
            if (node == null)
                return;
            Node<E> parent = node.getParent();
            if (parent == null)
                return;
            if (parent.compareTo(node) < 0)
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

        public Node<E> insert(int key, E element) throws Full {
            checkSize(1);
            if (isEmpty()) {
                Node<E> node = new Node<>(key, element, 0, this);
                setRoot(node);
                return node;
            }
            Node<E> parent = getParent(size);
            Node<E> node;
            if (parent.getLeft() == null) {
                node = addLeft(parent.getIndex(), key, element);
            } else {
                node = addRight(parent.getIndex(), key, element);
            }
            upHeap(node);
            return node;
        }

        public Node<E> pop() throws Empty {
            if (isEmpty())
                throw new Empty();
            Node<E> root = getRoot();
            swap(root, list.get(size - 1));
            list.set(root.getIndex(), null);
            downHeap(list.get(0));
            size--;
            return root;
        }

        public void visualize(Node<E> node, int level) {
            if (isEmpty() || node == null)
                return;
            if (node.getRight() != null)
                visualize(node.getRight(), level + 2);
            System.out.println(" ".repeat(4).repeat(level) + "-> " + node.getElement());
            if (node.getLeft() != null)
                visualize(node.getLeft(), level + 2);
        }

        public void visualize() {
            visualize(getRoot(), 0);
        }

        public static <T> boolean isLeaf(Node<T> node) {
            return !node.hasChildren();
        }

        public static <T> int height(Node<T> node) {
            if (node == null)
                return 0;
            else if (isLeaf(node))
                return 1;
            return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
        }

        public static <T> List<List<Node<T>>> getLayers(Node<T> node) {
            List<List<Node<T>>> output = new ArrayList<>();
            if (node == null) {
                return output;
            } else if (isLeaf(node)) {
                output.add(List.of(node));
                return output;
            }
            output.add(List.of(node));
            List<Node<T>> children = node.children();
            while (!children.isEmpty()) {
                output.add(children);
                List<Node<T>> childrenTemp = new ArrayList<>();
                for (Node<T> c : children) {
                    childrenTemp.addAll(c.children());
                }
                children = childrenTemp;
            }
            return output;
        }
    }
}
