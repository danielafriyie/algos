#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct _Node {
    void *element;
    struct _Node *parent;
    struct _Node *left;
    struct _Node *right;
    int index;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(void *element) {
    Node *node = malloc(NODE_SIZE);
    node->element = element;
    node->parent = NULL;
    node->left = NULL;
    node->right = NULL;
    node->index = -1;
    return node;
}

void Node_set_parent(Node *node, Node *parent) {
    if (node->parent != NULL)
        return;
    node->parent = parent;
}

void Node_set_left(Node *node, Node *left) {
    if (node->left != NULL)
        return;
    node->left = left;
}

void Node_set_right(Node *node, Node *right) {
    if (node->right != NULL)
        return;
    else if (node->left == NULL)
        return;
    node->right = right;
}

Node **Node_children(Node *node) {
    Node **children = malloc(NODE_SIZE * 2);
    if (node->left != NULL) {
        children[0] = node->left;
        if (node->right != NULL) {
            children[1] = node->right;
        }
    }
    return children;
}

bool Node_has_children(Node *node) {
    return node->left != NULL;
}

typedef struct _BinaryTree {

} BinaryTree;

int main() {

    return 0;
}
