#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define DEFAULT_CAPACITY 10
#define DEFAULT_MULTIPLIER 2
#define DEFAULT_LOAD_FACTOR = 0.75
#define DEFAULT_SHRINK_FACTOR 0.25

const int NULL_INT = -1;

typedef struct _Pair {
    void *key;
    void *value;
} Pair;

const size_t PAIR_SIZE = sizeof(Pair);

Pair *Pair_new(void *key, void *value) {
    Pair *pair = malloc(PAIR_SIZE);
    pair->key = key;
    pair->value = value;
    return pair;
}

char *Pair_get_key(Pair *pair) {
    return (pair == NULL) ? NULL : (char *)pair->key;
}

int Pair_get_value(Pair *pair) {
    return (pair == NULL) ? NULL_INT : *((int *)pair->value);
}

typedef struct _Node {
    Pair *pair;
    struct _Node *next;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(Pair *pair) {
    Node *node = malloc(NODE_SIZE);
    node->pair = pair;
    return node;
}

void Node_set_next(Node *parent, Node *child) {
    parent->next = child;
}



int main() {

    return 0;
}
