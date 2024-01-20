package arraybased;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnSortedPriorityQueue<E> {
    private final DoublyLinkedList<E> list;

    public UnSortedPriorityQueue() {
        this.list = new DoublyLinkedList<>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Node<E> insert(Number key, E element) {
        Node<E> node = new Node<>(element, key);
        list.append(node);
        return node;
    }

    public Node<E> min() {
        if (isEmpty())
            return null;
        Node<E> minNode = list.getHead();
        for (Node<E> node : list) {
            if (node.compareTo(minNode) < 0)
                minNode = node;
        }
        return minNode;
    }

    public Node<E> removeMin() {
        if (isEmpty())
            return null;
        Node<E> node = min();
        list.remove(node);
        return node;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Node<E> node : list) {
            builder.append(node);
            if (node.getNext() != null)
                builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }

    public static class Node<E> implements Comparable<Node<E>> {
        private E element;
        private final Number key;
        private Node<E> next;
        private Node<E> previous;

        public Node(E element, Number key) {
            this.element = element;
            this.key = key;
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

        @SuppressWarnings({"unchecked", "rawtypes"})
        public int compareTo(Node<E> node) {
            return ((Comparable) key).compareTo(node.key);
        };

        public String toString() {
            return String.format("%s(element=%s, key=%s)", getClass().getName(), element, key);
        }
    }

    private static class DoublyLinkedList<E> implements Iterable<Node<E>> {
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
            if (head == null)
                setHead(node);
            size++;
        }

        public void append(E element, Number key) {
            append(new Node<>(element, key));
        }

        public void prepend(Node<E> node) {
            setHead(node);
            if (tail == null)
                setTail(node);
            size++;
        }

        public void prepend(E element, Number key) {
            prepend(new Node<>(element, key));
        }

        public void remove(Node<E> node) {
            Node<E> next = node.getNext();
            Node<E> previous = node.getPrevious();
            if (next != null) {
                next.setPrevious(previous);
                if (node == head)
                    this.head = next;
            } else if (previous != null) {
                previous.setNext(null);
                if (node == tail)
                    this.tail = previous;
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

    public static void main(String[] args) {
        UnSortedPriorityQueue<Integer> queue = new UnSortedPriorityQueue<>();
        queue.insert(5, 23);
        queue.insert(1, 53);
        queue.insert(0, 324);
        System.out.println(queue.size());
        System.out.println(queue.min());
        System.out.println(queue);
    }
}
