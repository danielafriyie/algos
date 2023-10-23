import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> {
    private final DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public E pop() {
        Node<E> head = list.getHead();
        if (head == null)
            return null;
        Node<E> next = head.getNext();
        list.remove(head);
        list.setHead(null);
        list.setHead(next);
        return head.getElement();
    }

    public E top() {
        Node<E> head = list.getHead();
        return head != null ? head.getElement() : null;
    }

    public void push(E element) {
        list.prepend(element);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.size());
        System.out.println(stack.isEmpty());
        System.out.println(stack.top());
        System.out.println(stack.pop());
        System.out.println(stack.size());
    }

    public static class Node<E> {
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
            this.next = node;
            if (node == null)
                return;
            node.previous = this;
        }

        public Node<E> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<E> node) {
            this.previous = node;
            if (node == null)
                return;
            node.next = this;
        }

        public String toString() {
            E n = next != null ? next.element : null;
            E p = previous != null ? previous.element : null;
            return String.format("%s(element=%s, next=%s, previous=%s)", getClass().getName(), element, n, p);
        }
    }

    public static class DoublyLinkedList<E> implements Iterable<Node<E>> {
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

        public boolean isEmpty() {
            return size < 1;
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

        public void remove(Node<E> node) {
            Node<E> next = node.getNext();
            Node<E> previous = node.getPrevious();
            if (next != null) {
                next.setPrevious(previous);
            } else if (previous != null) {
                previous.setNext(null);
            }
            node.setNext(null);
            node.setPrevious(null);
            size--;
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
}
