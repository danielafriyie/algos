#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct _Node {
    void *element;
    struct _Node *previous;
    struct _Node *next;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *create_node(void *element) {
    Node *node = malloc(NODE_SIZE);
    node->element = element;
    node->next = NULL;
    node->previous = NULL;
    return node;
}

void set_next(Node *parent, Node *child) {
    parent->next = child;
    if (child != NULL) {
        child->previous = parent;
    }
}

void set_previous(Node *parent, Node *child) {
    parent->previous = child;
    if (child != NULL) {
        child->next = parent;
    }
}

typedef struct _DoublyLinkedList {
    Node *head;
    Node *tail;
    int size;
} DoublyLinkedList;

DoublyLinkedList create_list() {
    DoublyLinkedList list;
    list.head = NULL;
    list.tail = NULL;
    list.size = 0;
    return list;
}

bool is_empty(DoublyLinkedList list) {
    return list.size == 0;
}

void set_head(DoublyLinkedList *list, Node *node) {
    if (list->head != NULL) {
        set_previous(list->head, node);
    }
    list->head = node;
}

void set_tail(DoublyLinkedList *list, Node *node) {
    if (list->tail != NULL) {
        set_next(list->tail, node);
    }
    list->tail = node;
}

void append_node(DoublyLinkedList *list, Node *node) {
    set_tail(list, node);
    if (list->head == NULL) {
        set_head(list, node);
    }
    list->size++;
}

void prepend_node(DoublyLinkedList *list, Node *node) {
    set_head(list, node);
    if (list->tail == NULL) {
        set_tail(list, node);
    }
    list->size++;
}

void remove_node(DoublyLinkedList list, Node *node) {
    Node *next = node->next;
    Node *previous = node->previous;
    if (next != NULL) {
        next->previous = previous;
    } else if (previous != NULL) {
        previous->next = NULL;
    }

    node->next = NULL;
    node->previous = NULL;
    list.size--;
    free(node);
}

void print_node(Node *node) {
    int element = *((int *)node->element);
    int next_element = (node->next != NULL) ? *((int *)node->next->element) : NULL;
    int previous_element = (node->previous != NULL) ? *((int *)node->previous->element) : NULL;
    printf("Node(element=%d, next=%d, previous=%d)\n", element, next_element, previous_element);
}

void print_list(DoublyLinkedList list) {
    Node *next = list.head;
    if (next == NULL) {
        return;
    }

    while (next != NULL) {
        print_node(next);
        next = next->next;
    }
}

int main() {
    DoublyLinkedList list = create_list();
    printf("Empty: %d\n", is_empty(list));
    printf("Size before: %d\n", list.size);

    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    int e4 = 4;
    int e5 = 5;
    int e0 = 0;

    append_node(&list, create_node(&e1));
    append_node(&list, create_node(&e2));
    append_node(&list, create_node(&e3));
    append_node(&list, create_node(&e4));
    append_node(&list, create_node(&e5));
    prepend_node(&list, create_node(&e0));

    printf("Empty: %d\n", is_empty(list));
    printf("Size after: %d\n", list.size);
    print_list(list);

    return 0;
}
