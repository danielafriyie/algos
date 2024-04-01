#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define NULL_INT -1

typedef struct _Node {
    void *element;
    struct _Node *previous;
    struct _Node *next;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(void *element) {
    Node *node = (Node *)malloc(NODE_SIZE);
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

typedef struct _DoublyLinkedList {
    Node *head;
    Node *tail;
    int size;
} DoublyLinkedList;

const int LIST_SIZE = sizeof(DoublyLinkedList);

DoublyLinkedList *DoublyLinkedList_new() {
    DoublyLinkedList *list = (DoublyLinkedList *)malloc(LIST_SIZE);
    list->head = NULL;
    list->tail = NULL;
    list->size = 0;
    return list;
}

bool DoublyLinkedList_is_empty(DoublyLinkedList *list) {
    return list->size == 0;
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

void Node_print(Node *node) {
    int element = *((int *)node->element);
    int next_element = (node->next != NULL) ? *((int *)node->next->element) : NULL_INT;
    int previous_element = (node->previous != NULL) ? *((int *)node->previous->element) : NULL_INT;
    printf("Node(element=%d, next=%d, previous=%d)\n", element, next_element, previous_element);
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

void cleanup(DoublyLinkedList *list) {
    Node *node = list->head;
    Node *next;
    while (node != NULL) {
        next = node->next;
        free(node);
        node = next;
    }
    free(list);
}


int main() {
    DoublyLinkedList *list = DoublyLinkedList_new();
    printf("Empty: %d\n", DoublyLinkedList_is_empty(list));
    printf("Size before: %d\n", list->size);

    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    int e4 = 4;
    int e5 = 5;
    int e0 = 0;

    DoublyLinkedList_append(list, Node_new(&e1));
    DoublyLinkedList_append(list, Node_new(&e2));
    DoublyLinkedList_append(list, Node_new(&e3));
    DoublyLinkedList_append(list, Node_new(&e4));
    DoublyLinkedList_append(list, Node_new(&e5));
    DoublyLinkedList_prepend(list, Node_new(&e0));

    printf("Empty: %d\n", DoublyLinkedList_is_empty(list));
    printf("Size after: %d\n", list->size);
    DoublyLinkedList_print(list);
    cleanup(list);

    return 0;
}
