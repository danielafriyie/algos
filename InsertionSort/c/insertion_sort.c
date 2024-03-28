#include <stdbool.h>
#include <stdio.h>

void insertion_sort(int array[], bool reverse, int length) {
    if (length <= 1) {
        return;
    } else if (length == 2) {
        int i0 = array[0];
        int i1 = array[1];
        if ((i0 > i1) && (!reverse)) {
            array[0] = i1;
            array[1] = i0;
        }
        return;
    }

    for (int i = 1; i < length; i++) {
        int item = array[i];
        for (int j = i; j > 0; j--) {
            int prev_item = array[j - 1];
            if (prev_item > item) {
                array[j] = prev_item;
                array[j - 1] = item;
            }
        }
    }

    if (reverse) {
        int n = length / 2;
        for (int i = 0; i < n; i++) {
            int i0 = array[i];
            int i1 = array[length - 1 - i];
            array[i] = i1;
            array[length - 1 - i] = i0;
        }
    }
}

int main() {
    int array[] = {5, 4, 6, 8, 1, 0, 3, 8, 8, 5, 1, 7, 3, 10};
    int length = sizeof(array) / sizeof(array[0]);
    insertion_sort(array, true, length);

    for (int i = 0; i < length; i++) {
        printf("%d, ", array[i]);
    }
    printf("\n");

    return 0;
}
