void main() {
  List<num> arr1 = [5, 4, 6, 8, 1, 0, 3, 8, 8, 5, 1, 7, 3, 10];
  print("Before int: ${arr1}");
  insertionSort(arr1, true);
  print("Before int: ${arr1}\n");

  List<num> arr2 = [5.3, 4.3, 6.8, 8.14, 1.0, 0.6, 3.8, 8.26, 8.10, 5.39, 1.2, 7.98, 3.982];
  print("Before float: ${arr2}");
  insertionSort(arr2);
  print("Before float: ${arr2}");
}

void insertionSort<E extends Comparable<E>>(List<E> array, [bool reverse = false]) {
  int length = array.length;
  if (length <= 1) {
    return;
  } else if (length == 2) {
    E item0 = array[0];
    E item1 = array[1];
    if ((item0.compareTo(item1) > 0) && (!reverse)) {
      array[0] = item1;
      array[1] = item0;
      return;
    }
  }

  for (int i = 0; i < length; i++) {
    E item = array[i];
    for (int j = i; j > 0; j--) {
      E prevItem = array[j - 1];
      if (prevItem.compareTo(item) > 0) {
        array[j] = prevItem;
        array[j - 1] = item;
      }
    }
  }

  if (reverse) {
    int n = length ~/ 2;
    for (int i = 0; i < n; i++) {
      E item0 = array[i];
      E item1 = array[length - 1 - i];
      array[i] = item1;
      array[length - 1 - i] = item0;
    }
  }
}
