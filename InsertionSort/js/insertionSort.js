"use strict";

/**
 * Sorts an array in ascending or descending order.
 * Sorting is done in place.
 * Default is ascending order
 * @param {Array} array 
 * @param {Boolean} reverse 
 * @returns {void}
 */
function insertionSort(array, reverse) {
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

    if (reverse) {
        let n = parseInt(length / 2);
        for (let i = 0; i < n; i++) {
            let i0 = array[i];
            let i1 = array[length - 1 - i];
            array[i] = i1;
            array[length - 1 - 1] = i0;
        }
    }
}