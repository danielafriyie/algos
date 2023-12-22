void main() {
  DoublyLinkedList<int> lst = new DoublyLinkedList();
  lst.append(2);
  lst.append(4);
  lst.append(2);
  lst.prepend(46);
  print(lst.size);
  print(lst.head);
  print(lst.tail);

  print("\nLooping...\n");
  for (Node<int> n in lst) {
    print(n);
  }
}

class Node<E> {
  E _element;
  Node<E>? _next;
  Node<E>? _previous;

  Node(this._element, [Node<E>? next, Node<E>? previous]) {
    this._next = next;
    this._previous = previous;
  }

  E get element => _element;

  void set element(E element) {
    this._element = element;
  }

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

  @override
  String toString() {
    return "Node(element=$_element, next=${_next?._element}, previous=${_previous?._element})";
  }
}

class DoublyLinkedListIterator<E> implements Iterator<Node<E>> {
  Node<E>? _next;

  DoublyLinkedListIterator(this._next);

  Node<E> get current {
    Node<E> n = _next!;
    this._next = n.next;
    return n;
  }

  @override
  bool moveNext() {
    return _next != null;
  }
}

class DoublyLinkedList<E> extends Iterable<Node<E>> {
  Node<E>? _head;
  Node<E>? _tail;
  int _size = 0;

  DoublyLinkedList([Node<E>? head, Node<E>? tail]) {
    this._head = head;
    this._tail = tail;
  }

  Node<E>? get head => _head;

  void set head(Node<E>? node) {
    _head?.previous = node;
    this._head = node;
  }

  Node<E>? get tail => _tail;

  void set tail(Node<E>? node) {
    _tail?.next = node;
    this._tail = node;
  }

  int get size => _size;

  bool get isEmpty => _size < 1;

  void append(E element) {
    Node<E> node = Node(element);
    this.tail = node;
    if (_head == null) {
      this.head = node;
    }
    _size++;
  }

  void prepend(E element) {
    Node<E> node = Node(element);
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
  Iterator<Node<E>> get iterator => DoublyLinkedListIterator(_head);
}
