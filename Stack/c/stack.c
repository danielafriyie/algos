#include <stdio.h>

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
    int next_element = (node->next != NULL) ? *((int *)node->next->element) : NULL;
    int previous_element = (node->previous != NULL) ? *((int *)node->previous->element) : NULL;
    printf("Node(element=%d, next=%d, previous=%d)\n", element, next_element, previous_element);
}

Node *node_constructor(void *element) {
    Node *node = malloc(NODE_SIZE);
    node->element = element;
    node->next = NULL;
    node->previous = NULL;
    return node;
}

int main() {

    return 0;
}
