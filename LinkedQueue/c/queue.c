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

void Node_set_next(Node *parent, Node *child) {
    parent->next = child;
    if (child != NULL) {
        child->previous = parent;
    }
}

void Node_set_previous(Node *parent, Node *child) {
    parent->previous = child;
    if (child != NULL) {
        child->next = parent;
    }
}

void Node_set_element(Node *node, void *element) {
    node->element = element;
}

void Node_print(Node *node) {
    int element = *((int *)node->element);
    int next_element = (node->next != NULL) ? *((int *)node->next->element) : NULL_INT;
    int previous_element = (node->previous != NULL) ? *((int *)node->previous->element) : NULL_INT;
    printf("Node(element=%d, next=%d, previous=%d)\n", element, next_element, previous_element);
}

Node *Node_new(void *element) {
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

void DoublyLinkedList_set_head(DoublyLinkedList *list, Node *node) {
    if (list->head != NULL) {
        Node_set_previous(list->head, node);
    }
    list->head = node;
}

void DoublyLinkedList_set_tail(DoublyLinkedList *list, Node *node) {
    if (list->tail != NULL) {
        Node_set_next(list->tail, node);
    }
    list->tail = node;
}

bool DoublyLinkedList_is_empty(DoublyLinkedList *list) {
    return list->size < 1;
}

void DoublyLinkedList_append(DoublyLinkedList *list, Node *node) {
    DoublyLinkedList_set_tail(list, node);
    if (list->head == NULL) {
        DoublyLinkedList_set_head(list, node);
    }
    list->size++;
}

void DoublyLinkedList_prepend(DoublyLinkedList *list, Node *node) {
    DoublyLinkedList_set_head(list, node);
    if (list->tail == NULL) {
        DoublyLinkedList_set_tail(list, node);
    }
    list->size++;
}

void DoublyLinkedList_remove(DoublyLinkedList *list, Node *node, bool free_node) {
    Node *next = node->next;
    Node *previous = node->previous;
    if (next != NULL) {
        Node_set_previous(next, previous);
    } else if (previous != NULL) {
        Node_set_next(previous, NULL);
    }
    node->next = NULL;
    node->previous = NULL;
    list->size--;
    if (free_node) {
        free(node);
    }
}

DoublyLinkedList *DoublyLinkedList_new() {
    DoublyLinkedList *list = (DoublyLinkedList *)malloc(LIST_SIZE);
    list->head = NULL;
    list->tail = NULL;
    list->size = 0;
    return list;
}

void DoublyLinkedList_print(DoublyLinkedList *list) {
    Node *node = list->head;
    if (node == NULL) {
        return;
    }

    while (node != NULL) {
        Node_print(node);
        node = node->next;
    }
}

typedef struct _Queue {
    DoublyLinkedList *list;
} Queue;

const size_t QUEUE_SIZE = sizeof(Queue);

void *Queue_dequeue(Queue *queue) {
    Node *head = queue->list->head;
    if (head == NULL) {
        return NULL;
    }
    Node *next = head->next;
    DoublyLinkedList_remove(queue->list, head, false);
    DoublyLinkedList_set_head(queue->list, NULL);
    DoublyLinkedList_set_head(queue->list, next);
    void *element = head->element;
    free(head);
    return element;
}

void Queue_enqueue(Queue *queue, void *element) {
    Node *node = Node_new(element);
    DoublyLinkedList_append(queue->list, node);
}

void *Queue_first(Queue *queue) {
    Node *head = queue->list->head;
    return (head != NULL) ? head->element : NULL;
}

Queue *Queue_new() {
    DoublyLinkedList *list = DoublyLinkedList_new();
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
    Queue *queue = Queue_new();
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    Queue_enqueue(queue, &e1);
    Queue_enqueue(queue, &e2);
    Queue_enqueue(queue, &e3);
    printf("Size: %d\n", queue->list->size);
    int *val = (int *)Queue_dequeue(queue);
    printf("Dequeue value: %d\n", *val);
    printf("Size: %d\n", queue->list->size);
    printf("\n");
    DoublyLinkedList_print(queue->list);
    cleanup(queue);
    return 0;
}
