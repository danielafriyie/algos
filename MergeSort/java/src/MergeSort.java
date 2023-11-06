import java.util.Arrays;

public class MergeSort {

    public static <E extends Comparable<E>> E[] mergeSort(E[] array) {
        int length = array.length;
        if (length <= 1) {
            return array;
        } else if (length == 2) {
            E i0 = array[0];
            E i1 = array[1];
            if (i1.compareTo(i0) < 0) {
                array[0] = i1;
                array[1] = i0;
            }
            return array;
        }

        int mid = length / 2;
        E[] sortedLeft = mergeSort(Arrays.copyOfRange(array, 0, mid));
        E[] sortedRight = mergeSort(Arrays.copyOfRange(array, mid, length));
        int i = 0;
        int j = 0;
        int index = 0;
        int lenLeft = sortedLeft.length;
        int lenRight = sortedRight.length;
        @SuppressWarnings("unchecked") E[] output = (E[]) new Comparable[lenLeft + lenRight];

        while ((i < lenLeft) && (j < lenRight)) {
            if (sortedLeft[i].compareTo(sortedRight[j]) < 0) {
                output[index] = sortedLeft[i];
                i++;
            } else {
                output[index] = sortedRight[j];
                j++;
            }
            index++;
        }

        for (int k = i; k < lenLeft; k++) {
            output[index] = sortedLeft[k];
            index++;
        }

        for (int k = j; k < lenRight; k++) {
            output[index] = sortedRight[k];
            index++;
        }

        return output;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeSort(new Integer[]{12, 11, 13, 5, 6, 7})));
        System.out.println(Arrays.toString(mergeSort(new Integer[]{92, 0, 3, 6, 1, 5, 8, 45, 75, 12, 98, 3, 65})));
    }
}