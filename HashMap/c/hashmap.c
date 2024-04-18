#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NULL_INT -1
#define DEFAULT_CAPACITY 10
#define DEFAULT_MULTIPLIER 2
#define DEFAULT_LOAD_FACTOR 0.75

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

void Pair_print(Pair *pair) {
    char *key = Pair_get_key(pair);
    int value = Pair_get_value(pair);
    printf("Pair(key=%s, value=%d)\n", key, value);
}

typedef struct _Node {
    Pair *pair;
    struct _Node *next;
    struct _Node *previous;
} Node;

const size_t NODE_SIZE = sizeof(Node);

Node *Node_new(Pair *pair) {
    Node *node = malloc(NODE_SIZE);
    node->pair = pair;
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

typedef struct _LinkedList {
    unsigned int size;
    Node *head;
    Node *tail;
} LinkedList;

const size_t LINKEDLIST_SIZE = sizeof(LinkedList);

LinkedList *LinkedList_new() {
    LinkedList *list = malloc(LINKEDLIST_SIZE);
    list->size = 0;
    list->head = NULL;
    list->tail = NULL;
    return list;
}

bool LinkedList_is_empty(LinkedList *list) {
    return list->size < 1;
}

void LinkedList_append(LinkedList *list, Node *node) {
    if (LinkedList_is_empty(list)) {
        list->head = node;
        list->tail = node;
        list->size = 1;
        return;
    }

    Node_set_next(list->tail, node);
    list->tail = node;
    list->size++;
}

void LinkedList_remove(LinkedList *list, Node *node) {
    if (node == NULL)
        return;

    if (list->head == node) {
        list->head = node->next;
        if (list->head != NULL) {
            list->head->previous = NULL;
        }
    } else if (list->tail == node) {
        list->tail = node->previous;
        if (list->tail != NULL) {
            list->tail->next = NULL;
        }
    } else {
        Node_set_next(node->previous, node->next);
    }

    node->next = NULL;
    node->previous = NULL;
    list->size--;
    free(node);
}

typedef struct _HashMap {
    unsigned int size;
    unsigned int capacity;
    unsigned int multiplier;
    double load_factor;
    LinkedList **buckets;
    int (*hash_function)(struct _HashMap *map, void *);
    bool (*compare_function)(void *, void *);
} HashMap;

const size_t HASHMAP_SIZE = sizeof(HashMap);

HashMap *HashMap_new(
    unsigned int capacity,
    unsigned int multiplier,
    double load_factor,
    int (*hash_function)(HashMap *map, void *),
    bool (*compare_function)(void *, void *)) {
    HashMap *hash_map = malloc(HASHMAP_SIZE);
    hash_map->size = 0;
    hash_map->capacity = capacity;
    hash_map->multiplier = multiplier;
    hash_map->load_factor = load_factor;
    hash_map->buckets = calloc(capacity, LINKEDLIST_SIZE);
    hash_map->hash_function = hash_function;
    hash_map->compare_function = compare_function;
    return hash_map;
}

bool HashMap_is_empty(HashMap *map) {
    return map->size < 1;
}

Pair **HashMap_get_pairs(HashMap *map) {
    if (HashMap_is_empty(map))
        return NULL;

    Pair **pairs = calloc(map->size, PAIR_SIZE);
    int index = 0;
    int pairs_index = 0;
    while (index < map->capacity) {
        LinkedList *list = map->buckets[index];
        if (list != NULL) {
            Node *node = list->head;
            while (node != NULL) {
                Pair *pair = node->pair;
                pairs[pairs_index] = pair;
                pairs_index++;
            }
        }
        index++;
    }

    return pairs;
}

void **HashMap_keys(HashMap *map) {
    if (HashMap_is_empty(map))
        return NULL;

    void **keys = calloc(map->size, sizeof(void *));
    Pair **pairs = HashMap_get_pairs(map);
    for (int i = 0; i < map->size; i++) {
        keys[i] = pairs[i]->key;
    }

    free(pairs);
    return keys;
}

void **HashMap_values(HashMap *map) {
    if (HashMap_is_empty(map))
        return NULL;

    void **values = calloc(map->size, sizeof(void *));
    Pair **pairs = HashMap_get_pairs(map);
    for (int i = 0; i < map->size; i++) {
        values[i] = pairs[i]->value;
    }

    free(pairs);
    return values;
}

LinkedList *HashMap_get_bucket(HashMap *map, void *key) {
    int index = map->hash_function(map, key);
    LinkedList *list = map->buckets[index];
    return list;
}

LinkedList *HashMap_get_or_create_bucket(HashMap *map, void *key) {
    int index = map->hash_function(map, key);
    LinkedList *list = map->buckets[index];
    if (list == NULL) {
        list = LinkedList_new();
        map->buckets[index] = list;
    }

    return list;
}

void *HashMap_get_and_remove(HashMap *map, void *key, bool remove) {
    LinkedList *list = HashMap_get_bucket(map, key);
    if (list == NULL)
        return NULL;

    Node *node = list->head;
    while (node != NULL) {
        if (map->compare_function(node->pair->key, key)) {
            void *value = node->pair->value;
            if (remove) {
                LinkedList_remove(list, node);
            }
            return value;
        }
        node = node->next;
    }

    return NULL;
}

void *HashMap_get(HashMap *map, void *key) {
    return HashMap_get_and_remove(map, key, false);
}

bool HashMap_load_factor_exceeded(HashMap *map) {
    return (map->size / (double)map->capacity) >= map->load_factor;
}

void HashMap_put_no_check(HashMap *map, void *key, void *value) {
    LinkedList *list = HashMap_get_or_create_bucket(map, key);
    Node *node = list->head;
    while (node != NULL) {
        if (node->pair->key == key) {
            node->pair->value = value;
            map->size++;
            return;
        }

        node = node->next;
    }

    Pair *pair = Pair_new(key, value);
    node = Node_new(pair);
    LinkedList_append(list, node);
    map->size++;
}

void HashMap_resize_and_rehash(HashMap *map) {
    unsigned int old_capacity = map->capacity;
    LinkedList **old_buckets = map->buckets;

    map->size = 0;
    map->capacity *= map->multiplier;
    map->buckets = calloc(map->capacity, LINKEDLIST_SIZE);

    int index = 0;
    while (index < old_capacity) {
        LinkedList *list = old_buckets[index];
        if (list != NULL) {
            Node *node = list->head;
            while (node != NULL) {
                void *key = node->pair->key;
                void *value = node->pair->value;
                HashMap_put_no_check(map, key, value);
                node = node->next;
            }
        }
        index++;
    }

    free(old_buckets);
}

void HashMap_put(HashMap *map, void *key, void *value) {
    if (HashMap_load_factor_exceeded(map)) {
        HashMap_resize_and_rehash(map);
    }

    HashMap_put_no_check(map, key, value);
}

bool HashMap_contains(HashMap *map, void *key) {
    return HashMap_get(map, key) != NULL;
}

void *HashMap_remove(HashMap *map, void *key) {
    if (HashMap_contains(map, key)) {
        map->size--;
    }
    return HashMap_get_and_remove(map, key, true);
}

// HASH FUNCTIONS
int HashMap_hash_char(HashMap *map, void *key) {
    int length = strlen(key);
    return length % map->capacity;
}

bool HashMap_compare_char(void *key1, void *key2) {
    return strcmp(key1, key2) == 0;
}

void cleanup(HashMap *map) {
    int index = 0;
    while (index < map->capacity) {
        LinkedList *list = map->buckets[index];
        if (list != NULL) {
            Node *node = list->head;
            while (node != NULL) {
                Pair *pair = node->pair;
                Node *next = node->next;

                Pair_print(pair);
                free(pair);
                free(node);
                node = next;
            }

            free(list);
        }
        index++;
    }

    free(map->buckets);
    free(map);
}

int main() {
    char key1[] = "one";
    int value1 = 1;

    char key2[] = "two";
    int value2 = 2;

    char key3[] = "three";
    int value3 = 3;

    char key4[] = "four";
    int value4 = 4;


    HashMap *map = HashMap_new(DEFAULT_CAPACITY, DEFAULT_MULTIPLIER, DEFAULT_LOAD_FACTOR, HashMap_hash_char, HashMap_compare_char);
    HashMap_put(map, &key1, &value1);
    HashMap_put(map, &key2, &value2);
    HashMap_put(map, &key3, &value3);
    HashMap_put(map, &key4, &value4);

    printf("Size: %d\nContains: %d\n", map->size, HashMap_contains(map, "easy"));
    printf("Key: three, Remove: %d\n", *((int *)HashMap_remove(map, "three")));
    printf("Get: four, value: %d\n", *((int *)HashMap_get(map, &key4)));
    printf("Size: %d\n", map->size);
    cleanup(map);

    return 0;
}
