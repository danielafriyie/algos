public class Deque<E> {
    private final DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public E first() {
        Node<E> head = list.getHead();
        return head != null ? head.getElement() : null;
    }

    public void addFirst(E element) {
        list.prepend(element);
    }

    public E removeFirst() {
        Node<E> head = list.getHead();
        if (head == null)
            return null;
        Node<E> next = head.getNext();
        list.remove(head);
        list.setHead(null);
        list.setHead(next);
        return head.getElement();
    }

    public E last() {
        Node<E> last = list.getTail();
        return last != null ? last.getElement() : null;
    }

    public void addLast(E element) {
        list.append(element);
    }

    public E removeLast() {
        Node<E> tail = list.getTail();
        if (tail == null)
            return null;
        Node<E> previous = tail.getPrevious();
        if (previous != null)
            previous.setNext(null);
        list.remove(tail);
        list.setTail(null);
        list.setTail(previous);
        return tail.getElement();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<>();
        queue.addFirst(2);
        queue.addLast(3);
        queue.addFirst(4);
        System.out.println(queue.first());
        System.out.println(queue.last());
    }
}
