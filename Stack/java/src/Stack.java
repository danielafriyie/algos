public class Stack<E> {
    private final DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public void push(E element) {
        list.prepend(element);
    }

    public int size() {
        return list.size();
    }


}
