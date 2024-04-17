#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define NULL_INT -1

typedef struct _Node {
    int key;
    void *element;
    struct _Node *previous;
    struct _Node *next;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(int key, void *element) {
    Node *node = malloc(NODE_SIZE);
    node->key = key;
    node->element = element;
    node->next = NULL;
    node->previous = NULL;
    return node;
}

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

void Node_print(Node *node) {
    int element = *((int *)node->element);
    printf("Node(key=%d, element=%d)\n", node->key, element);
}

int Node_get_element(Node *node) {
    if (node == NULL)
        return NULL_INT;
    return *((int *)node->element);
}

bool Node_gt(Node *node, Node *other) {
    return node->key > other->key;
}

bool Node_lt(Node *node, Node *other) {
    return node->key < other->key;
}

typedef struct _DoublyLinkedList {
    Node *head;
    Node *tail;
    int size;
} DoublyLinkedList;

const int LIST_SIZE = sizeof(DoublyLinkedList);

DoublyLinkedList *DoublyLinkedList_new() {
    DoublyLinkedList *list = malloc(LIST_SIZE);
    list->head = NULL;
    list->tail = NULL;
    list->size = 0;
    return list;
}

bool DoublyLinkedList_is_empty(DoublyLinkedList *list) {
    return list->size < 1;
}

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

void DoublyLinkedList_remove(DoublyLinkedList *list, Node *node) {
    Node *next = node->next;
    Node *previous = node->previous;
    if (next != NULL) {
        next->previous = previous;
    } else if (previous != NULL) {
        previous->next = NULL;
    }

    node->next = NULL;
    node->previous = NULL;
    list->size--;
    free(node);
}

void DoublyLinkedList_insert_before(DoublyLinkedList *list, Node *node1, Node *node2) {
    if ((node2->previous != NULL) || (node2->next != NULL))
        return;
    if (node1->previous != NULL)
        Node_set_next(node1->previous, node2);
    Node_set_next(node2, node1);
    if (node1 == list->head)
        list->head = node2;
    list->size++;
}

void DoublyLinkedList_insert_after(DoublyLinkedList *list, Node *node1, Node *node2) {
    if ((node2->previous != NULL) || (node2->next != NULL))
        return;
    if (node1->next != NULL)
        Node_set_previous(node1->next, node2);
    Node_set_next(node1, node2);
    if (node1 == list->tail)
        list->tail = node2;
    list->size++;
}

void DoublyLinkedList_print(DoublyLinkedList *list) {
    Node *next = list->head;
    if (next == NULL) {
        return;
    }

    while (next != NULL) {
        Node_print(next);
        next = next->next;
    }
}

typedef struct _PriorityQueue {
    DoublyLinkedList *list;
} PriorityQueue;

const size_t PRIORITY_QUEUE_SIZE = sizeof(PriorityQueue);

PriorityQueue *PriorityQueue_new() {
    DoublyLinkedList *list = DoublyLinkedList_new();
    PriorityQueue *queue = malloc(PRIORITY_QUEUE_SIZE);
    queue->list = list;
    return queue;
}

int PriorityQueue_size(PriorityQueue *queue) {
    return queue->list->size;
}

bool PriorityQueue_is_empty(PriorityQueue *queue) {
    return DoublyLinkedList_is_empty(queue->list);
}

Node *PriorityQueue_insert(PriorityQueue *queue, int key, void *element) {
    Node *new_node = Node_new(key, element);
    DoublyLinkedList_append(queue->list, new_node);
    return new_node;
}

Node *PriorityQueue_min(PriorityQueue *queue) {
    if (PriorityQueue_is_empty(queue))
        return NULL;
    return queue->list->tail;
}

void *PriorityQueue_remove_min(PriorityQueue *queue) {
    if (PriorityQueue_is_empty(queue))
        return NULL;
    Node *node = queue->list->tail;
    void *element = node->element;
    DoublyLinkedList_remove(queue->list, node);
    return element;
}

void cleanup(PriorityQueue *queue) {
    Node *node = queue->list->head;
    Node *next;
    while (node != NULL) {
        next = node->next;
        free(node);
        node = next;
    }
    free(queue->list);
    free(queue);
}

int main() {
    int e1 = 324;
    int e2 = 53;
    int e3 = 23;
    int e4 = 32;
    int e5 = 2;
    int e6 = 23234;
    PriorityQueue *queue = PriorityQueue_new();
    PriorityQueue_insert(queue, 0, &e1);
    PriorityQueue_insert(queue, 1, &e2);
    PriorityQueue_insert(queue, 5, &e3);
    PriorityQueue_insert(queue, 4, &e4);
    PriorityQueue_insert(queue, 10, &e5);
    PriorityQueue_insert(queue, 3, &e6);

    printf("Size: %d\nEmpty: %d\nMin node: ", PriorityQueue_size(queue), PriorityQueue_is_empty(queue));
    Node_print(PriorityQueue_min(queue));
    DoublyLinkedList_print(queue->list);

    cleanup(queue);
    return 0;
}
