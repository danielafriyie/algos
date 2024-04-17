#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define NULL_INT -1

int max(int a, int b) {
    return (a >= b) ? a : b;
}

typedef struct _Node {
    int key;
    void *element;
    int index;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(int key, void *element, int index) {
    Node *node = malloc(NODE_SIZE);
    node->key = key;
    node->element = element;
    node->index = index;
    return node;
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

void Node_print(Node *node) {
    if (node == NULL)
        return;

    printf("Node(k=%d, element=%d)\n", node->key, Node_get_element(node));
}

typedef struct _MinHeapBinaryTree {
    int maxsize;
    int size;
    Node **list;
} MinHeapBinaryTree;

const size_t TREE_SIZE = sizeof(MinHeapBinaryTree);

MinHeapBinaryTree *MinHeapBinaryTree_new(int maxsize) {
    MinHeapBinaryTree *tree = malloc(TREE_SIZE);
    tree->maxsize = maxsize;
    tree->size = 0;
    tree->list = calloc(maxsize, NODE_SIZE);
    return tree;
}

bool MinHeapBinaryTree_is_empty(MinHeapBinaryTree *tree) {
    return tree->size < 1;
}

bool MinHeapBinaryTree_is_full(MinHeapBinaryTree *tree) {
    return tree->size >= tree->maxsize;
}

Node *MinHeapBinaryTree_get_root(MinHeapBinaryTree *tree) {
    return tree->list[0];
}

void MinHeapBinaryTree_set_root(MinHeapBinaryTree *tree, Node *node) {
    tree->list[0] = node;
    tree->size = 1;
}

bool MinHeapBinaryTree_check_size(MinHeapBinaryTree *tree, int n) {
    return (tree->size + n) > tree->maxsize;
}

Node *MinHeapBinaryTree_get_parent(MinHeapBinaryTree *tree, int index) {
    if (index == 0)
        return NULL;
    return tree->list[(index - 1) / 2];
}

Node *MinHeapBinaryTree_get_node_parent(MinHeapBinaryTree *tree, Node *node) {
    return MinHeapBinaryTree_get_parent(tree, node->index);
}

Node *MinHeapBinaryTree_add_left(MinHeapBinaryTree *tree, int index, int key, void *element) {
    if (MinHeapBinaryTree_check_size(tree, 1))
        return NULL;

    int i = (2 * index) + 1;
    Node *node = Node_new(key, element, i);
    tree->list[i] = node;
    tree->size++;
    return node;
}

Node *MinHeapBinaryTree_get_left(MinHeapBinaryTree *tree, int index) {
    return tree->list[(2 * index) + 1];
}

Node *MinHeapBinaryTree_get_node_left(MinHeapBinaryTree *tree, Node *node) {
    return (node == NULL) ? NULL : MinHeapBinaryTree_get_left(tree, node->index);
}

Node *MinHeapBinaryTree_add_right(MinHeapBinaryTree *tree, int index, int key, void *element) {
    if (MinHeapBinaryTree_check_size(tree, 1))
        return NULL;

    int i = (2 * index) + 2;
    Node *node = Node_new(key, element, i);
    tree->list[i] = node;
    tree->size++;
    return node;
}

Node *MinHeapBinaryTree_get_right(MinHeapBinaryTree *tree, int index) {
    return tree->list[(2 * index) + 2];
}

Node *MinHeapBinaryTree_get_node_right(MinHeapBinaryTree *tree, Node *node) {
    return (node == NULL) ? NULL : MinHeapBinaryTree_get_right(tree, node->index);
}

bool MinHeapBinaryTree_is_root(MinHeapBinaryTree *tree, Node *node) {
    if (MinHeapBinaryTree_is_empty(tree))
        return false;

    return tree->list[0] == node;
}

void MinHeapBinaryTree_swap(MinHeapBinaryTree *tree, Node *parent, Node *child) {
    if ((parent == NULL) || (child == NULL))
        return;

    int pindex = parent->index;
    int cindex = child->index;
    tree->list[pindex] = child;
    tree->list[cindex] = parent;
    parent->index = cindex;
    child->index = pindex;
}

void MinHeapBinaryTree_up_heap(MinHeapBinaryTree *tree, Node *node) {
    if (node == NULL)
        return;

    Node *parent = MinHeapBinaryTree_get_node_parent(tree, node);
    if (parent == NULL)
        return;

    if (Node_lt(parent, node))
        return;

    MinHeapBinaryTree_swap(tree, parent, node);
    MinHeapBinaryTree_up_heap(tree, node);
}

void MinHeapBinaryTree_down_heap(MinHeapBinaryTree *tree, Node *node) {
    if (node == NULL)
        return;

    Node *left = MinHeapBinaryTree_get_node_left(tree, node);
    Node *right = MinHeapBinaryTree_get_node_right(tree, node);

    if (left != NULL) {
        Node *child_to_swap = left;
        if (right != NULL) {
            if (Node_lt(right, left)) {
                child_to_swap = right;
            }
        }

        MinHeapBinaryTree_swap(tree, node, child_to_swap);
        MinHeapBinaryTree_down_heap(tree, node);
    }
}

Node *MinHeapBinaryTree_insert(MinHeapBinaryTree *tree, int key, void *element) {
    if (MinHeapBinaryTree_check_size(tree, 1))
        return NULL;

    if (MinHeapBinaryTree_is_empty(tree)) {
        Node *node = Node_new(key, element, 0);
        MinHeapBinaryTree_set_root(tree, node);
        return node;
    }

    Node *node;
    Node *parent = MinHeapBinaryTree_get_parent(tree, tree->size);
    if (MinHeapBinaryTree_get_left(tree, parent->index) == NULL) {
        node = MinHeapBinaryTree_add_left(tree, parent->index, key, element);
    } else {
        node = MinHeapBinaryTree_add_right(tree, parent->index, key, element);
    }

    MinHeapBinaryTree_up_heap(tree, node);
    return node;
}

Node *MinHeapBinaryTree_pop(MinHeapBinaryTree *tree) {
    if (MinHeapBinaryTree_is_empty(tree))
        return NULL;

    Node *root = MinHeapBinaryTree_get_root(tree);
    MinHeapBinaryTree_swap(tree, root, tree->list[tree->size - 1]);
    tree->list[root->index] = NULL;
    MinHeapBinaryTree_down_heap(tree, tree->list[0]);
    tree->size--;
    return root;
}

bool MinHeapBinaryTree_is_leaf(MinHeapBinaryTree *tree, Node *node) {
    return MinHeapBinaryTree_get_node_left(tree, node) == NULL;
}

int MinHeapBinaryTree_node_height(MinHeapBinaryTree *tree, Node *node) {
    if (node == NULL)
        return 0;
    else if (MinHeapBinaryTree_is_leaf(tree, node))
        return 1;

    return 1 + max(MinHeapBinaryTree_node_height(tree, MinHeapBinaryTree_get_node_left(tree, node)), MinHeapBinaryTree_node_height(tree, MinHeapBinaryTree_get_node_right(tree, node)));
}

void MinHeapBinaryTree_visualize_tree(MinHeapBinaryTree *tree, Node *node, int level) {
    Node *right = MinHeapBinaryTree_get_node_right(tree, node);
    if (right != NULL)
        MinHeapBinaryTree_visualize_tree(tree, right, level + 4);

    for (int i = 0; i < level; i++) {
        printf(" ");
    }

    int value = Node_get_element(node);
    printf("->%d\n", value);

    Node *left = MinHeapBinaryTree_get_node_left(tree, node);
    if (left != NULL)
        MinHeapBinaryTree_visualize_tree(tree, left, level + 4);
}

void MinHeapBinaryTree_visualize_left_to_right(MinHeapBinaryTree *tree) {
    if (MinHeapBinaryTree_get_root(tree) == NULL)
        return;
        
    MinHeapBinaryTree_visualize_tree(tree, MinHeapBinaryTree_get_root(tree), 0);
}

typedef struct _PriorityQueue {
    MinHeapBinaryTree *list;
} PriorityQueue;

const size_t PRIORITY_QUEUE_SIZE = sizeof(PriorityQueue);

PriorityQueue *PriorityQueue_new(int maxsize) {
    PriorityQueue *queue = malloc(PRIORITY_QUEUE_SIZE);
    queue->list = MinHeapBinaryTree_new(maxsize);
    return queue;
}

int PriorityQueue_size(PriorityQueue *queue) {
    return queue->list->size;
}

bool PriorityQueue_is_empty(PriorityQueue *queue) {
    return MinHeapBinaryTree_is_empty(queue->list);
}

Node *PriorityQueue_insert(PriorityQueue *queue, int key, void *element) {
    return MinHeapBinaryTree_insert(queue->list, key, element);
}

Node *PriorityQueue_min(PriorityQueue *queue) {
    if (PriorityQueue_is_empty(queue))
        return NULL;

    return MinHeapBinaryTree_get_root(queue->list);
}

Node *PriorityQueue_remove_min(PriorityQueue *queue) {
    return MinHeapBinaryTree_pop(queue->list);
}

void cleanup(PriorityQueue *queue) {
    for (int i = 0; i < queue->list->size; i++) {
        Node *node = queue->list->list[i];
        if (node != NULL)
            free(node);
    }

    free(queue->list->list);
    free(queue->list);
    free(queue);
}

int main() {
    int e1 = 1;
    int e2 = 2;
    int e3 = 3;
    int e4 = 4;
    int e5 = 5;
    int e6 = 6;

    PriorityQueue *queue = PriorityQueue_new(6);
    PriorityQueue_insert(queue, e5, &e5);
    PriorityQueue_insert(queue, e3, &e3);
    PriorityQueue_insert(queue, e2, &e2);
    PriorityQueue_insert(queue, e6, &e6);
    PriorityQueue_insert(queue, e4, &e4);
    PriorityQueue_insert(queue, e1, &e1);

    printf("Root: ");
    Node_print(MinHeapBinaryTree_get_root(queue->list));
    printf("Size: %d\nEmpty: %d\nMin node: ", PriorityQueue_size(queue), PriorityQueue_is_empty(queue));
    Node_print(PriorityQueue_min(queue));
    printf("\n");
    MinHeapBinaryTree_visualize_left_to_right(queue->list);

    cleanup(queue);
    return 0;
}
