import java.util.Arrays;

public class InsertionSort {

    /**
     * Sorts array in ascending or descending order.
     * Sorting is done in place.
     * Default is ascending order.
     * @param reverse whether to sort in ascending or descending order, true=desc, false=asc
     */
    public static <E extends Comparable<E>> void sort(E[] array, boolean reverse) {
        int length = array.length;
        if (length <= 1) {
            return;
        }

        if (length == 2) {
            E item0 = array[0];
            E item1 = array[1];
            if (item0.compareTo(item1) > 0 && !reverse) {
                array[0] = item1;
                array[1] = item0;
            }
            return;
        }

        for (int i = 1; i < length; i++) {
            E item = array[i];
            for (int j = i; j > 0; j--) {
                E prevItem = array[j - 1];
                if (prevItem.compareTo(item) <= 0)
                    break;
                array[j - 1] = item;
                array[j] = prevItem;
            }
        }

        if (reverse) {
            int n = length / 2;
            for (int i = 0; i < n; i++) {
                E item0 = array[i];
                E item1 = array[length - 1 - i];
                array[i] = item1;
                array[length - 1 - i] = item0;
            }
        }
    }

    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, false);
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{5, 4, 6, 8, 1, 0, 3, 8, 8, 5, 1, 7, 3, 10};
        System.out.println("Before int: " + Arrays.toString(array));
        sort(array, true);
        System.out.println("After int: " + Arrays.toString(array) + "\n");

        Double[] array2 = new Double[]{5.3, 4.3, 6.8, 8.14, 1.0, 0.6, 3.8, 8.26, 8.10, 5.39, 1.2, 7.98, 3.982};
        System.out.println("Before double: " + Arrays.toString(array2));
        sort(array2, true);
        System.out.println("After double: " + Arrays.toString(array2) + "\n");

        Character[] array3 = new Character[]{'a', 'A', 'c', 'a', 'z', 'c', 'Y', '2', '5', 'd'};
        System.out.println("Before char: " + Arrays.toString(array3));
        sort(array3);
        System.out.println("After char: " + Arrays.toString(array3));
    }
}