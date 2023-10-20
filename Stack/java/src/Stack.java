public class Stack<E> {
    private final DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public E pop() {
        Node<E> head = list.getHead();
        if (head == null)
            return null;
        list.setHead(head.getNext());
        if (list.getHead() != null)
            list.getHead().setPrevious(null);
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
}
