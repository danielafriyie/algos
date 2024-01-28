import java.util.Iterator;

public class PriorityQueue<E> implements Iterable<Node<E>> {
    private final MinHeapBinaryTree<E> tree;

    public PriorityQueue(int maxSize) {
        this.tree = new MinHeapBinaryTree<>(maxSize, null);
    }

    public PriorityQueue() {
        this.tree = new MinHeapBinaryTree<>();
    }

    public int size() {
        return tree.getSize();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public Node<E> insert(int key, E element) throws MinHeapBinaryTree.Full {
        return tree.insert(key, element);
    }

    public Node<E> min() {
        return (isEmpty()) ? null : tree.getRoot();
    }

    public Node<E> removeMin() throws MinHeapBinaryTree.Empty {
        return tree.pop();
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return tree.iterator();
    }
}
