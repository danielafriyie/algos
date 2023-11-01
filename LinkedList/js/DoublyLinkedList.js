"use strict";


class Node {

    constructor(element) {
        this._element = element;
        this._next = null;
        this._previous = null;
    }

    get next() {
        return this._next;
    }

    set next(node) {
        this._next = node;
        if (node != null) {
            node._previous = this;
        }
    }

    get previous() {
        return this._previous;
    }

    set previous(node) {
        this._previous = node;
        if (node != null) {
            node._next = this;
        }
    }

    get element() {
        return this._element;
    }

    set element(element) {
        this._element = element;
    }

    toString() {
        let n = this._next != null ? this._next.element : null;
        let p = this._previous != null ? this._previous.element : null;
        return `Node(element=${this._element}, next=${n}, previous=${p})`;
    }
}

class DoublyLinkedList {

    constructor() {
        this._head = null;
        this._tail = null;
        this._size = 0;
    }

    get size() {
        return this._size;
    }

    get head() {
        return this._head;
    }

    set head(node) {
        if (this._head != null) {
            this._head.previous = node;
        }
        this._head = node;
    }

    get tail() {
        return this._tail;
    }

    set tail(node) {
        if (this._tail != null) {
            this._tail.next = node;
        }
        this._tail = node;
    }

    get isEmpty() {
        return this._size < 1;
    }

    append(node) {
        this.tail = node;
        if (this._head === null) {
            this.head = node;
        }
        this._size++;
    }

    prepend(node) {
        this.head = node;
        if (this._tail === null) {
            this.tail = node;
        }
        this._size++;
    }

    remove(node) {
        let next = node.next;
        let previous = node.previous;
        if (next != null) {
            next.previous = previous;
        } else if (previous != null) {
            previous.next = null;
        }
        node.next = null;
        node.previous = null;
        this._size--;
    }
}

const lst = new DoublyLinkedList();
lst.append(new Node(1));
lst.append(new Node(2));
lst.append(new Node(3));
lst.append(new Node(5));
lst.prepend(new Node(0));
console.log(lst.size);
console.log(lst.head);
console.log(lst.tail);
