#include <stdio.h>
#include <stdbool.h>

bool binary_search(int array[], int start, int length, int target) {
    if (length <= 0) 
        return false;
    else if (length == 1)
        return array[0] == target;
    
    int mid = length / 2;
    int mid_index = mid + start;
    int mid_value = array[mid_index];
    if (mid_value == target)
        return true;
    
    if (target < mid_value)
        return binary_search(array, start, mid_index, target);
    return binary_search(array, mid_index, length - mid, target);
}

int main() {
    int array[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int length = sizeof(array) / sizeof(array[0]);
    printf("Result for '%d': %d\n", 3, binary_search(array, 0, length, 3));
    printf("Result for '%d': %d\n", 9, binary_search(array, 0, length, 9));
    printf("Result for '%d': %d\n", 32, binary_search(array, 0, length, 32));

    return 0;
}
