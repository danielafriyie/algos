#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

int max(int a, int b) {
    return (a >= b) ? a : b;
}

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

bool Node_has_children(Node *node) {
    return node->left != NULL;
}

int Node_height(Node *node) {
    if (node == NULL)
        return 0;
    else if (!Node_has_children(node))
        return 1;
    return 1 + max(Node_height(node->left), Node_height(node->right));
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

void Node_print(Node *node) {
    int value = *((int *) node->element);
    printf("Node(%d)\n", value);
}

typedef struct _BinaryTree {
    Node *root;
    int size;
} BinaryTree;

size_t BINARY_TREE_SIZE = sizeof(BinaryTree);

BinaryTree *BinaryTree_new() {
    BinaryTree *tree = malloc(BINARY_TREE_SIZE);
    tree->root = NULL;
    tree->size = 0;
    return tree;
}

bool BinaryTree_empty(BinaryTree *tree) {
    return tree->size < 1;
}

int BinaryTree_height(BinaryTree *tree) {
    return Node_height(tree->root);
}

void BinaryTree_set_root(BinaryTree *tree, Node *node) {
    if (tree->root != NULL)
        return;
    tree->root = node;
}

void BinaryTree_visualize_left_to_right(BinaryTree *tree) {
    if (tree->root == NULL)
        return;
    
    void _vis(Node *node, int level) {
        if (node->right != NULL)
            _vis(node->right, level + 2);
        
        int strings = level + 4;
        for (int i = 0; i < strings; i++) {
            printf(" ");
        }

        int value = *((int *) node->element);
        printf("->%d\n", value);

        if (node->left != NULL)
            _vis(node->left, level + 2);
    }

    _vis(tree->root, 1);
}

int main() {

    return 0;
}
