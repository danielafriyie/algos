#include <stdio.h>

void merge_sort(int array[], int start, int end, int length) {
    if (length <= 1) {
        return;
    } else if (length == 2) {
        int i0 = array[start];
        int i1 = array[start + 1];
        if (i1 < i0) {
            array[start] = i1;
            array[start + 1] = i0;
        }
        return;
    }

    int mid = length / 2;
    merge_sort(array, start, mid, mid);                // sort left
    merge_sort(array, mid + start, end, length - mid); // sort right

    int left_index = start;
    int left_length = start + mid;

    int right_index = start + mid;
    int right_length = end;

    int counter = 0;
    int temp_array[end - start];

    while ((left_index < left_length) && (right_index < right_length)) {
        int left_val = array[left_index];
        int right_val = array[right_index];
        if (left_val < right_val) {
            temp_array[counter] = left_val;
            left_index++;
        } else {
            temp_array[counter] = right_val;
            right_index++;
        }

        counter++;
    }

    // add remaing left values
    while (left_index < left_length) {
        temp_array[counter] = array[left_index];
        left_index++;
        counter++;
    }

    // add remaining right values
    while (right_index < right_length) {
        temp_array[counter] = array[right_index];
        right_index++;
        counter++;
    }

    // copy/replace temp array values to main array
    int temp_array_length = end - start;
    for (int k = 0; k < temp_array_length; k++) {
        array[start + k] = temp_array[k];
    }
}

void print_array(int array[], int length) {
    for (int i = 0; i < length; i++) {
        printf("%d, ", array[i]);
    }
    printf("\n");
}

int main() {
    int array1[] = {12, 11, 13, 5, 6, 7};
    int length = sizeof(array1) / sizeof(array1[0]);
    merge_sort(array1, 0, length, length);
    print_array(array1, length);

    int array2[] = {92, 0, 3, 6, 1, 5, 8, 45, 75, 12, 98, 3, 65};
    int length2 = sizeof(array2) / sizeof(array2[0]);
    merge_sort(array2, 0, length2, length2);
    print_array(array2, length2);

    return 0;
}