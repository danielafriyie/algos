package doubly;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements Iterable<Node<E>>{
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public DoublyLinkedList(Node<E> head, Node<E> tail) {
        this.head = head;
        this.tail = tail;
    }

    public DoublyLinkedList(Node<E> head) {
        this(head, null);
    }

    public DoublyLinkedList() {
        this(null);
    }

    private void increaseSize() {
        size++;
    }

    public void setHead(Node<E> node) {
        if (head != null) {
            head.setPrevious(node);
        }
        this.head = node;
    }

    public Node<E> getHead() {
        return head;
    }

    public void setTail(Node<E> node) {
        if (tail != null) {
            tail.setNext(node);
        }
        this.tail = node;
    }

    public Node<E> getTail() {
        return tail;
    }

    public int size() {
        return size;
    }

    public void append(Node<E> node) {
        setTail(node);
        increaseSize();
        if (head == null)
            setHead(node);
    }

    public void append(E element) {
        append(new Node<>(element));
    }

    public void prepend(Node<E> node) {
        setHead(node);
        increaseSize();
        if (tail == null)
            setTail(node);
    }

    public void prepend(E element) {
        prepend(new Node<>(element));
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class DoublyLinkedListIterator implements Iterator<Node<E>> {
        private Node<E> next;

        public DoublyLinkedListIterator() {
            this.next = DoublyLinkedList.this.head;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node<E> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<E> n = next;
            this.next = n.getNext();
            return n;
        }
    }
}
