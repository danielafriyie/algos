#include <stdio.h>

void array_copy(int source[], int copy[], int start, int end) {
    for (int i = start; i < end; i++) {
        copy[i - start] = source[i];
    }
}

int *merge_sort(int array[]) {
    int length = sizeof(array) / sizeof(array[0]);
    if (length <= 1) {
        return array;
    } else if (length == 2) {
        int i0 = array[0];
        int i1 = array[1];
        if (i1 < i0) {
            array[0] = i1;
            array[1] = i0;
        }
        return array;
    }

    int mid = length / 2;
    
    int left_array[mid];
    array_copy(array, left_array, 0, mid);
    int sorted_left[] = merge_sort(left_array);
    
    int right_array[length - mid];
    array_copy(array, right_array, mid, length);
    int sorted_right[] = merge_sort(right_array);

    
}

int main() {
    int array[] = {92, 0};
    int sorted[] = merge_sort(array);
    int length = sizeof(sorted) / sizeof(sorted[0]);
    for (int i = 0; i < length; i++) {
        print("%d, ", sorted[i]);
    }
    printf("\n");

    return 0;
}