package doubly;

public class Node<E> {
    private E element;
    private Node<E> next;
    private Node<E> previous;

    public Node(E element, Node<E> next, Node<E> previous) {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }

    public Node(E element, Node<E> next) {
        this(element, next, null);
    }

    public Node(E element) {
        this(element, null);
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> node) {
        if (node == null)
            return;
        this.next = node;
        node.previous = this;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<E> node) {
        if (node == null)
            return;
        this.previous = node;
        node.next = this;
    }

    public String toString() {
        E n = next != null ? next.element : null;
        E p = previous != null ? previous.element : null;
        return String.format("%s(element=%s, next=%s, previous=%s)", getClass().getName(), element, n, p);
    }
}
