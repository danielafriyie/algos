#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

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

void remove_node(DoublyLinkedList *list, Node *node) {
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

typedef struct _Stack {
    DoublyLinkedList *list;
} Stack;

const size_t STACK_SIZE = sizeof(Stack);

void *pop(Stack *stack) {
    Node *head = stack->list->head;
    if (head == NULL) {
        return NULL;
    }
    Node *next = head->next;
    remove_node(stack->list, head);
    set_head(stack->list, NULL);
    set_head(stack->list, next);
    return head->element;
}

void *top(Stack *stack) {
    Node *head = stack->list->head;
    return (head != NULL) ? head->element : NULL;
}

void push(Stack *stack, void *element) {
    Node *node = node_constructor(element);
    prepend(stack->list, node);
}

Stack *stack_constructor() {
    DoublyLinkedList *list = list_constructor();
    Stack *stack = (Stack *)malloc(STACK_SIZE);
    stack->list = list;
    return stack;
}

void cleanup(Stack *stack) {
    DoublyLinkedList *list = stack->list;
    Node *node = list->head;
    Node *next;
    while (node != NULL) {
        next = node->next;
        free(node);
        node = next;
    }
    free(list);
    free(stack);
}

int main() {
    Stack *stack = stack_constructor();
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    push(stack, &e1);
    push(stack, &e2);
    push(stack, &e3);
    printf("Size: %d\n", stack->list->size);
    int *val = (int *)pop(stack);
    printf("Pop value: %d\n", *val);
    printf("Size: %d\n", stack->list->size);
    printf("\n");
    print_list(stack->list);
    cleanup(stack);

    return 0;
}
