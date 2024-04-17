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

int main() {
    

    return 0;
}
