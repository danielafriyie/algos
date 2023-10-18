import java.util.Arrays;

public class InsertionSort {

    /*
    * Sorts array in ascending or descending order.
    * */
    public static <E extends Comparable<E>> void sort(E[] array, boolean reverse) {
        int length = array.length;
        if (length == 2) {
            E item0 = array[0];
            E item1 = array[1];
            int result = item0.compareTo(item1);
            if (result < 0) {
                array[0] = item1;
                array[1] = item0;
            }
        } else if (length > 2) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < (length - 1); j++) {
                    E item = array[i];
                    E nextItem = array[j + 1];

                }
            }
        }
    }

    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, true);
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 2};
        sort(array, false);
        System.out.println(Arrays.toString(array));
    }
}