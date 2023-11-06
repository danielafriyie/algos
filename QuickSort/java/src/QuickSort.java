import java.util.Arrays;

public class QuickSort {

    public static <E extends Comparable<E>> int partition(E[] array, int low, int high) {
        E pivot = array[high];
        int index = low;
        for (int i = low; i < high; i++) {
            E currentItem = array[i], swapItem = array[index];
            if (currentItem.compareTo(pivot) <= 0) {
                array[i] = swapItem;
                array[index] = currentItem;
                index++;
            }
        }

        if (index > 0) {
            array[high] = array[index];
            array[index] = pivot;
        }

        return index;
    }

    public static <E extends Comparable<E>> void quickSort(E[] array, int low, int high) {
        int length = array.length;
        if (length <= 1) {
            return;
        } else if (length == 2) {
            E i0 = array[0], i1 = array[1];
            if (i0.compareTo(i1) > 0) {
                array[0] = i1;
                array[1] = i0;
            }
            return;
        }

        if ((low >= high) || (low < 0))
            return;

        int index = partition(array, low, high);
        if (index <= 0)
            return;

        quickSort(array, low, index - 1);
        quickSort(array, index + 1, high);
    }

    public static <E extends Comparable<E>> void quickSort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        Integer[][] arrays = {
                {1, 2, 3, 4, 5},
                {10, 7, 8, 9, 1, 5},
                {3, 6, 21, 3, 5, 7, 676, 98, 67, 346, 6, 5, 4, 8, 0, 2, 4, 2, 7, 3, 5, 25}
        };

        for (Integer[] array : arrays) {
            System.out.println(Arrays.toString(array));
            quickSort(array);
            System.out.println(Arrays.toString(array));
            System.out.println();
        }
    }
}
