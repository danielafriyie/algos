import java.util.Arrays;

public class BinarySearch {

    public static <E extends Comparable<E>> boolean binarySearch(E[] array, E target) {
        int length = array.length;
        if (length == 0)
            return false;
        else if (length == 1)
            return array[0] == target;
        int mid = length / 2;
        E midValue = array[mid];
        int result = midValue.compareTo(target);
        if (result == 0)
            return true;
        else if (result > 0)
            return binarySearch(Arrays.copyOfRange(array, 0, mid), target);
        return binarySearch(Arrays.copyOfRange(array, mid + 1, length), target);
    }

    public static void main(String[] args) {
        System.out.println(binarySearch(new Integer[]{1, 2, 3, 4, 6}, 3));
        System.out.println(binarySearch(new Double[]{1.6, 2.5, 43.78, 54.0, 99.983}, 2.5));
        System.out.println(binarySearch(new Double[]{1.6, 2.5, 43.78, 54.0, 99.983}, 2.556));
        System.out.println(binarySearch(new Character[]{'a', 'b', 'c', 'd', 'e', 'f'}, 'c'));
    }
}
