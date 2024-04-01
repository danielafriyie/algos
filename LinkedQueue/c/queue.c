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

typedef struct _Queue {
    DoublyLinkedList *list;
} Queue;

const size_t QUEUE_SIZE = sizeof(Queue);

void *dequeue(Queue *queue) {
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

void enqueue(Queue *queue, void *element) {
    Node *node = node_constructor(element);
    append(queue->list, node);
}

void *first(Queue *queue) {
    Node *head = queue->list->head;
    return (head != NULL) ? head->element : NULL;
}

Queue *queue_constructor() {
    DoublyLinkedList *list = list_constructor();
    Queue *queue = (Queue *)malloc(QUEUE_SIZE);
    queue->list = list;
    return queue;
}

void cleanup(Queue *queue) {
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
    Queue *queue = queue_constructor();
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    enqueue(queue, &e1);
    enqueue(queue, &e2);
    enqueue(queue, &e3);
    printf("Size: %d\n", queue->list->size);
    int *val = (int *)dequeue(queue);
    printf("Dequeue value: %d\n", *val);
    printf("Size: %d\n", queue->list->size);
    printf("\n");
    print_list(queue->list);
    cleanup(queue);
    return 0;
}
