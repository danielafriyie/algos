void main() {
  Stack<int> stack = Stack();
  stack.push(1);
  stack.push(2);
  stack.push(3);
  print(stack.size);
  print(stack.pop());
  for (Node<int> node in stack) {
    print(node.element);
  }
}

class Node<E> {
  E _element;
  Node<E>? _next;
  Node<E>? _previous;

  Node({required E element, Node<E>? next, Node<E>? previous})
      : _element = element,
        _next = next,
        _previous = previous;

  Node<E>? get next => _next;

  void set next(Node<E>? node) {
    this._next = node;
    node?._previous = this;
  }

  Node<E>? get previous => _previous;

  void set previous(Node<E>? node) {
    this._previous = node;
    node?._next = this;
  }

  E get element => _element;

  void set element(E element) {
    this._element = element;
  }

  @override
  String toString() {
    return "Node(element=$_element, next=$_next, previous=$_previous)";
  }
}

class _DoublyLinkedListIterator<E> implements Iterator<Node<E>> {
  Node<E>? _next;
  DoublyLinkedList<E> list;

  _DoublyLinkedListIterator(this.list) {
    this._next = list.head;
  }

  @override
  bool moveNext() {
    return _next != null;
  }

  @override
  Node<E> get current {
    Node<E> n = _next!;
    this._next = n.next;
    return n;
  }
}

class DoublyLinkedList<E> extends Iterable<Node<E>> {
  Node<E>? _head;
  Node<E>? _tail;
  int _size = 0;

  DoublyLinkedList({Node<E>? head, Node<E>? tail}) {
    this._head = head;
    this._tail = tail;
  }

  int get size => _size;

  Node<E>? get head => _head;

  void set head(Node<E>? node) {
    this._head?.previous = node;
    this._head = node;
  }

  Node<E>? get tail => _tail;

  void set tail(Node<E>? node) {
    this._tail?.previous = node;
    this._tail = node;
  }

  bool get empty => _size < 1;

  void append(Node<E> node) {
    this.tail = node;
    if (_head == null) {
      this.head = node;
    }
    _size++;
  }

  void prepend(Node<E> node) {
    this.head = node;
    if (_tail == null) {
      this.tail = node;
    }
    _size++;
  }

  void remove(Node<E> node) {
    Node<E>? next = node.next;
    Node<E>? previous = node.previous;
    if (next != null) {
      next.previous = previous;
    } else if (previous != null) {
      previous.next = null;
    }
    node.next = null;
    node.previous = null;
    _size--;
  }

  @override
  Iterator<Node<E>> get iterator => _DoublyLinkedListIterator(this);
}

class Stack<E> extends Iterable<Node<E>> {
  final DoublyLinkedList<E> _list = DoublyLinkedList();

  int get size => _list.size;

  bool get empty => _list.empty;

  E? pop() {
    Node<E>? head = _list.head;
    if (head == null) return null;
    Node<E>? next = head.next;
    _list.remove(head);
    _list.head = null;
    _list.head = next;
    return head.element;
  }

  E? top() {
    return _list.head?.element;
  }

  void push(E element) {
    _list.prepend(Node(element: element));
  }

  @override
  Iterator<Node<E>> get iterator => _list.iterator;
}
