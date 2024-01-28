import java.util.List;
import java.util.ArrayList;

public class Node<E> implements Comparable<Node<E>> {
    private final int key;
    private final E element;
    private MinHeapBinaryTree<E> tree;
    private Node<E> parent;
    private Node<E> left;
    private Node<E> right;
    private Node<E> nextSibling;
    private Node<E> previousSibling;

    public Node(int key, E element, MinHeapBinaryTree<E> tree) {
        this.key = key;
        this.element = element;
        this.tree = tree;
    }

    public Node(int key, E element) {
        this(key, element, null);
    }

    public E getElement() {
        return element;
    }

    public MinHeapBinaryTree<E> getTree() {
        return tree;
    }

    public void setTree(MinHeapBinaryTree<E> tree) throws IllegalStateException {
        if ((tree != null) && (this.tree != null)) {
            throw new IllegalStateException("Tree is not empty!");
        }
        this.tree = tree;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> node) {
        this.parent = node;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> node) {
        this.left = node;
        if (node != null)
            node.setParent(this);

    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> node) throws IllegalArgumentException {
        this.right = node;
        if (node != null)
            node.setParent(this);
    }

    public Node<E> getNextSibling() {
        return nextSibling;
    }

    public void setNextSibling(Node<E> node) {
        this.nextSibling = node;
        if (node != null)
            node.previousSibling = this;
    }

    public Node<E> getPreviousSibling() {
        return previousSibling;
    }

    public void setPreviousSibling(Node<E> node) {
        this.previousSibling = node;
        if (node != null)
            node.nextSibling = this;
    }

    public boolean isFull() {
        return (left != null) && (right != null);
    }

    public List<Node<E>> children() {
        List<Node<E>> output = new ArrayList<>(2);
        if (left != null) {
            output.add(left);
            if (right != null) {
                output.add(right);
            }
        }

        return output;
    }

    public boolean hasChildren() {
        return (left != null) || (right != null);
    }

    @Override
    public String toString() {
        return String.format("Node(key=%s, element=%s)", key, element);
    }

    @Override
    public int compareTo(Node<E> other) {
        return Integer.compare(key, other.key);
    }
}
