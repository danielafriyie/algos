public class Queue<E> {
    private final DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public E dequeue() {
        Node<E> head = list.getHead();
        if (head == null)
            return null;
        Node<E> next = head.getNext();
        list.remove(head);
        list.setHead(null);
        list.setHead(next);
        return head.getElement();
    }

    public E first() {
        Node<E> head = list.getHead();
        return head != null ? head.getElement() : null;
    }

    public void enqueue(E element) {
        list.append(element);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        System.out.println(queue.first());
        System.out.println(queue.dequeue());
        System.out.println(queue.size());
        System.out.println(queue.first());
    }
}
