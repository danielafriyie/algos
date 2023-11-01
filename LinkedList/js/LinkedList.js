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
    }

    get previous() {
        return this._previous;
    }

    set previous(node) {
        this._previous = node;
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
        return `${this.constructor.name}(element=${this._element}, next=${n}, previous=${p})`;
    }
}
