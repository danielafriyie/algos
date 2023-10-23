import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedPriorityQueue<E> {
    private final DoublyLinkedList<E> list;

    public SortedPriorityQueue() {
        this.list = new DoublyLinkedList<>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Node<E> insert(Number key, E element) {
        Node<E> newNode = new Node<>(element, key);
        if (list.isEmpty()) {
            list.append(newNode);
        } else {
            Node<E> node = list.head;
            while (node != null) {
                if (newNode.compareTo(node) > 0) {
                    list.insertBefore(node, newNode);
                    break;
                }
                node = node.getNext();
            }
        }
        return newNode;
    }

    public Node<E> min() {
        return list.getTail();
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

        /**
         * Inserts node2 before node1.
         * @param node1 inserts after node2
         * @param node2 inserts before node1
         * @throws IllegalArgumentException if node2 has a next or previous
         */
        public void insertBefore(Node<E> node1, Node<E> node2) {
            if (node2.getPrevious() != null || node2.getNext() != null)
                throw new IllegalArgumentException();
            if (node1.getPrevious() != null) {
                node1.getPrevious().setNext(node2);
            }
            node2.setNext(node1);
            if (node1 == head)
                this.head = node2;
            size++;
        }

        /**
         * Inserts node2 after node1.
         * @param node1 inserts before node2
         * @param node2 inserts after node1
         * @throws IllegalArgumentException if node2 has a next or previous
         */
        public void insertAfter(Node<E> node1, Node<E> node2) {
            if (node2.getPrevious() != null || node2.getNext() != null)
                throw new IllegalArgumentException();
            if (node1.getNext() != null) {
                node1.getNext().setPrevious(node2);
            }
            node1.setNext(node2);
            if (node1 == tail)
                this.tail = node2;
            size++;
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
        SortedPriorityQueue<Integer> queue = new SortedPriorityQueue<>();
        queue.insert(0, 324);
        queue.insert(1, 53);
        queue.insert(5, 23);
        queue.insert(4, 32);
        queue.insert(10, 2);
        queue.insert(3, 23234);
        System.out.println(queue.size());
        System.out.println(queue.min());
        System.out.println(queue);
    }
}
