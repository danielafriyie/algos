"use strict";

/**
 * Sorts an array in ascending order.
 * Sorting is done in place.
 * @param {Array} array 
 * @returns {void}
 */
function insertionSort(array) {
    let length = array.length;
    if (length <= 1) {
        return;
    } else if (length === 2) {
        let [i0, i1] = array;
        if ((i0 > i1) && !reverse) {
            array[0] = i1;
            array[1] = i0;
        }
        return;
    }

    for (let i = 0; i < length; i++) {
        let item = array[i];
        for (let j = i; j > 0; j--) {
            let prevItem = array[j - 1];
            if (prevItem > item) {
                array[j] = prevItem;
                array[j - 1] = item;
            }
        }
    }
}

const arr1 = [5, 4, 6, 8, 1, 0, 3, 8, 8, 5, 1, 7, 3, 10];
console.log(`Before int: ${arr1}`);
insertionSort(arr1);
console.log(`After int: ${arr1}\n`);
