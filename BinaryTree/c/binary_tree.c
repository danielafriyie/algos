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
    if (node->left == NULL)
        return NULL;

    Node **children = calloc(2, NODE_SIZE);
    children[0] = node->left;
    if (node->right != NULL) {
        children[1] = node->right;
    }

    return children;
}

void Node_print(Node *node) {
    if (node == NULL)
        return;

    int value = *((int *)node->element);
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

Node *BinaryTree_add_left(BinaryTree *tree, Node *parent, void *element) {
    Node *node = Node_new(element);
    if (tree->root == NULL) {
        BinaryTree_set_root(tree, node);
        tree->size++;
        return node;
    }
    Node_set_left(parent, node);
    tree->size++;
    return node;
}

Node *BinaryTree_add_right(BinaryTree *tree, Node *parent, void *element) {
    Node *node = Node_new(element);
    if (tree->root == NULL) {
        BinaryTree_set_root(tree, node);
        tree->size++;
        return node;
    }
    Node_set_right(parent, node);
    tree->size++;
    return node;
}

void BinaryTree_preorder_traversal(Node *node) {
    if (node == NULL)
        return;

    Node_print(node);

    if (node->left != NULL) {
        BinaryTree_preorder_traversal(node->left);
    }

    if (node->right != NULL) {
        BinaryTree_preorder_traversal(node->right);
    }
}

void BinaryTree_postorder_traversal(Node *node) {
    if (node == NULL)
        return;

    if (!Node_has_children(node)) {
        Node_print(node);
        return;
    }

    if (node->left != NULL) {
        BinaryTree_postorder_traversal(node->left);
    }

    if (node->right != NULL) {
        BinaryTree_postorder_traversal(node->right);
    }

    Node_print(node);
}

void BinaryTree_inorder_traversal(Node *node) {
    if (node == NULL) {
        return;
    }

    if (node->left != NULL) {
        BinaryTree_postorder_traversal(node->left);
    }

    Node_print(node);

    if (node->right != NULL) {
        BinaryTree_postorder_traversal(node->right);
    }
}

void BinaryTree_breath_first_traversal(Node *node, bool print_node, bool continue_traversal) {
    if (node == NULL)
        return;

    Node_print(node);
    if (!Node_has_children(node))
        return;

    Node **children = Node_children(node);
    int depth_size = 4, current_size = 2;
    while (true) {
        int counter = 0;
        Node **temp_array = calloc(depth_size, NODE_SIZE);
        for (int i = 0; i < current_size; i++) {
            Node *child = children[i];
            if (child != NULL) {
                Node_print(child);
                Node **childs_children = Node_children(child);
                if (childs_children != NULL) {
                    for (int j = 0; j < 2; j++) {
                        Node *childs_child = childs_children[j];
                        if (childs_child != NULL) {
                            temp_array[counter] = childs_child;
                            counter++;
                        }
                    }
                }
            }
        }

        free(children);
        if (counter == 0) {
            free(temp_array);
            break;
        }

        children = temp_array;
        current_size = depth_size;
        depth_size *= 2;
    }
}

void BinaryTree_visualize_tree(BinaryTree *tree, Node *node, int level) {
    if (node->right != NULL)
        BinaryTree_visualize_tree(tree, node->right, level + 4);

    for (int i = 0; i < level; i++) {
        printf(" ");
    }

    int value = *((int *)node->element);
    printf("->%d\n", value);

    if (node->left != NULL)
        BinaryTree_visualize_tree(tree, node->left, level + 4);
}

void BinaryTree_visualize_left_to_right(BinaryTree *tree) {
    if (tree->root == NULL)
        return;

    BinaryTree_visualize_tree(tree, tree->root, 0);
}

void cleanup(Node *node) {
    if (node == NULL)
        return;
    cleanup(node->left);
    cleanup(node->right);
    free(node);
}

int main() {
    int e0 = 0;
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    int e4 = 4;
    int e5 = 5;
    int e6 = 6;
    int e7 = 7;

    BinaryTree *tree = BinaryTree_new();
    BinaryTree_set_root(tree, Node_new(&e0));

    BinaryTree_add_left(tree, tree->root, &e1);
    BinaryTree_add_right(tree, tree->root, &e2);

    BinaryTree_add_left(tree, tree->root->left, &e3);
    BinaryTree_add_right(tree, tree->root->left, &e4);

    BinaryTree_add_left(tree, tree->root->right, &e5);
    BinaryTree_add_right(tree, tree->root->right, &e6);

    BinaryTree_add_left(tree, tree->root->left->left, &e7);

    printf("Height: %d\nSize: %d\nEmpty: %d\n", BinaryTree_height(tree), tree->size, BinaryTree_empty(tree));

    printf("\nIn-order traversal\n");
    BinaryTree_inorder_traversal(tree->root);

    printf("\nPre-order traversal\n");
    BinaryTree_preorder_traversal(tree->root);

    printf("\nPost order traversal\n");
    BinaryTree_postorder_traversal(tree->root);

    printf("\nBreath first traversal\n");
    BinaryTree_breath_first_traversal(tree->root, true, true);

    printf("\nVisualize left to right\n");
    BinaryTree_visualize_left_to_right(tree);

    cleanup(tree->root);
    free(tree);
    return 0;
}
