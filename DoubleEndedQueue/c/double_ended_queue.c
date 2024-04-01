#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define NULL_INT -1

typedef struct _Node {
    void *element;
    struct _Node *next;
    struct _Node *previous;
} Node;

const size_t NODE_SIZE = sizeof(Node);

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

void set_element(Node *node, void *element) {
    node->element = element;
}

void print_node(Node *node) {
    int element = *((int *)node->element);
    int next_element = (node->next != NULL) ? *((int *)node->next->element) : NULL_INT;
    int previous_element = (node->previous != NULL) ? *((int *)node->previous->element) : NULL_INT;
    printf("Node(element=%d, next=%d, previous=%d)\n", element, next_element, previous_element);
}

Node *node_constructor(void *element) {
    Node *node = (Node *)malloc(NODE_SIZE);
    node->element = element;
    node->next = NULL;
    node->previous = NULL;
    return node;
}

typedef struct _DoublyLinkedList {
    Node *head;
    Node *tail;
    int size;
} DoublyLinkedList;

const size_t LIST_SIZE = sizeof(DoublyLinkedList);

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

bool is_empty(DoublyLinkedList *list) {
    return list->size < 1;
}

void append(DoublyLinkedList *list, Node *node) {
    set_tail(list, node);
    if (list->head == NULL) {
        set_head(list, node);
    }
    list->size++;
}

void prepend(DoublyLinkedList *list, Node *node) {
    set_head(list, node);
    if (list->tail == NULL) {
        set_tail(list, node);
    }
    list->size++;
}

void remove_node(DoublyLinkedList *list, Node *node, bool free_node) {
    Node *next = node->next;
    Node *previous = node->previous;
    if (next != NULL) {
        set_previous(next, previous);
    } else if (previous != NULL) {
        set_next(previous, NULL);
    }
    node->next = NULL;
    node->previous = NULL;
    list->size--;
    if (free_node) {
        free(node);
    }
}

DoublyLinkedList *list_constructor() {
    DoublyLinkedList *list = (DoublyLinkedList *)malloc(LIST_SIZE);
    list->head = NULL;
    list->tail = NULL;
    list->size = 0;
    return list;
}

void print_list(DoublyLinkedList *list) {
    Node *node = list->head;
    if (node == NULL) {
        return;
    }

    while (node != NULL) {
        print_node(node);
        node = node->next;
    }
}

typedef struct _DoubleEndedQueue {
    DoublyLinkedList *list;
} DoubleEndedQueue;

const size_t QUEUE_SIZE = sizeof(DoubleEndedQueue);

void *get_element(Node *node) {
    return (node != NULL) ? node->element : NULL;
}

void *first(DoubleEndedQueue *queue) {
    return get_element(queue->list->head);
}

void add_first(DoubleEndedQueue *queue, void *element) {
    prepend(queue->list, node_constructor(element));
}

void *remove_fist(DoubleEndedQueue *queue) {
    Node *head = queue->list->head;
    if (head == NULL) {
        return NULL;
    }
    Node *next = head->next;
    remove_node(queue->list, head, false);
    set_head(queue->list, NULL);
    set_head(queue->list, next);
    void *element = head->element;
    free(head);
    return element;
}

void *last(DoubleEndedQueue *queue) {
    return get_element(queue->list->tail);
}

void add_last(DoubleEndedQueue *queue, void *element) {
    append(queue->list, node_constructor(element));
}

void *remove_last(DoubleEndedQueue *queue) {
    Node *tail = queue->list->tail;
    if (tail == NULL) {
        return NULL;
    }
    Node *previous = tail->previous;
    if (previous != NULL) {
        set_next(previous, NULL);
    }
    remove_node(queue->list, tail, false);
    set_tail(queue->list, NULL);
    set_tail(queue->list, previous);
    void *element = tail->element;
    free(tail);
    return element;
}

DoubleEndedQueue *queue_constructor() {
    DoublyLinkedList *list = list_constructor();
    DoubleEndedQueue *queue = (DoubleEndedQueue *)malloc(QUEUE_SIZE);
    queue->list = list;
    return queue;
}

void cleanup(DoubleEndedQueue *queue) {
    DoublyLinkedList *list = queue->list;
    Node *node = list->head;
    Node *next;
    while (node != NULL) {
        next = node->next;
        free(node);
        node = next;
    }
    free(list);
    free(queue);
}

int main() {
    DoubleEndedQueue *queue = queue_constructor();
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    add_first(queue, &e1);
    add_last(queue, &e2);
    add_first(queue, &e3);
    printf("Size: %d\n", queue->list->size);
    printf("\n");
    print_list(queue->list);
    cleanup(queue);
    return 0;
}
