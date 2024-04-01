#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define DEFAULT_CAPACITY 10
#define DEFAULT_MULTIPLIER 2
#define DEFAULT_SHRINK_FACTOR 0.25

typedef struct _ArrayList {
    int **array;
    unsigned int size;
    unsigned int capacity;
    unsigned int multiplier;
    double shrink_factor;
} ArrayList;

const size_t LIST_SIZE = sizeof(ArrayList);

bool ArrayList_is_full(ArrayList *list) {
    return list->size >= list->capacity;
}

bool ArrayList_is_empty(ArrayList *list) {
    return list->size <= 0;
}

bool ArrayList_check_size(int index, int size) {
    return index >= size;
}

void ArrayList_resize_array(ArrayList *list, int size) {
    list->array = realloc(list->array, size * sizeof(int*));
}

void ArrayList_grow_array(ArrayList *list) {
    if (ArrayList_is_full(list)) {
        list->capacity *= list->multiplier;
        ArrayList_resize_array(list, list->capacity);
    }
}

void ArrayList_shrink_array(ArrayList *list) {
    int n = list->shrink_factor * list->capacity;
    if (list->size < n) {
        list->capacity = n;
        ArrayList_resize_array(list, list->capacity);
    }
}

void ArrayList_insert_item(ArrayList *list, int index, int *item) {
    ArrayList_grow_array(list);
    if (index == list->size) {
        list->array[list->size] = item;
    } else {
        for (int i = list->size; i > index; i--) {
            list->array[i] = list->array[i - 1];
        }
        list->array[index] = item;
    }
    list->size++;
}

int *ArrayList_get_item(ArrayList *list, int index) {
    return list->array[index];
}

int *ArrayList_set_item(ArrayList *list, int index, int *item) {
    int *current_item = list->array[index];
    list->array[index] = item;
    return current_item;
}

int *ArrayList_remove_item(ArrayList *list, int index) {
    int *current_item = list->array[index];
    for (int i = index; i < list->size; i++) {
        if ((i + 1) == list->size) {
            list->array[i] = NULL;
        } else {
            list->array[i] = list->array[i + 1];
        }
    }
    list->size--;
    ArrayList_shrink_array(list);
    return current_item;
}

void ArrayList_append(ArrayList *list, int *item) {
    ArrayList_insert_item(list, list->size, item);
}

ArrayList *ArrayList_new(int capacity, int multiplier, double shrink_factor) {
    ArrayList *list = (ArrayList *)malloc(LIST_SIZE);
    list->array = calloc(capacity, sizeof(int*));
    list->size = 0;
    list->capacity = capacity;
    list->multiplier = multiplier;
    list->shrink_factor = shrink_factor;
    return list;
}

void ArrayList_print(ArrayList *list) {
    printf("List items: ");
    for (int i = 0; i < list->size; i++) {
        printf("%d, ", *list->array[i]);
    }

    printf("\n");
}

int main() {
    ArrayList *list = ArrayList_new(DEFAULT_CAPACITY, DEFAULT_MULTIPLIER, DEFAULT_SHRINK_FACTOR);
    int i1 = 1;
    int i2 = 2;
    int i3 = 3;
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    ArrayList_append(list, &i1);
    ArrayList_append(list, &i2);
    ArrayList_append(list, &i3);
    printf("Size: %d\n", list->size);
    printf("Remove: %d\n", *ArrayList_remove_item(list, 0));
    printf("Size: %d\n", list->size);
    ArrayList_print(list);
    free(list->array);
    free(list);
    return 0;
}
