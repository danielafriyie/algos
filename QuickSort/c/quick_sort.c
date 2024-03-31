#include <stdio.h>

int partition(int array[], int low, int high) {
    int pivot = array[high];
    int index = low;
    for (int i = low; i < high; i++) {
        int current_item = array[i];
        int swap_item = array[index];
        if (current_item <= pivot) {
            array[i] = swap_item;
            array[index] = current_item;
            index++;
        }
    }

    if (index > 0) {
        array[high] = array[index];
        array[index] = pivot;
    }

    return index;
}

void quick_sort(int array[], int low, int high) {
    if ((low >= high) || (low < 0))
        return;

    int index = partition(array, low, high);

    quick_sort(array, low, index - 1);
    quick_sort(array, index + 1, high);
}

void print_array(int array[], int length) {
    for (int i = 0; i < length; i++) {
        printf("%d, ", array[i]);
    }
    printf("\n");
}

int main() {
    int arr1[] = {1, 2, 3, 4, 5};
    int arr1_length = sizeof(arr1) / sizeof(arr1[0]);
    quick_sort(arr1, 0, arr1_length - 1);
    print_array(arr1, arr1_length);

    int arr2[] = {10, 7, 8, 9, 1, 5};
    int arr2_length = sizeof(arr2) / sizeof(arr2[0]);
    quick_sort(arr2, 0, arr2_length - 1);
    print_array(arr2, arr2_length);

    int arr3[] = {3, 6, 21, 3, 5, 7, 676, 98, 67, 346, 6, 5, 4, 8, 0, 2, 4, 2, 7, 3, 5, 25};
    int arr3_length = sizeof(arr3) / sizeof(arr3[0]);
    quick_sort(arr3, 0, arr3_length - 1);
    print_array(arr3, arr3_length);

    return 0;
}
