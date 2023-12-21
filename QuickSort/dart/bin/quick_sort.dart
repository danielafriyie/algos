void main() {
  List<List<num>> lsts = [
    [1, 2, 3, 4, 5],
    [10, 7, 8, 9, 1, 5],
    [3, 6, 21, 3, 5, 7, 676, 98, 67, 346, 6, 5, 4, 8, 0, 2, 4, 2, 7, 3, 5, 25],
  ];
  for (List<num> lst in lsts) {
    print(lst);
    quickSort(lst);
    print(lst);
    print("");
  }
}

int partition<E extends Comparable<E>>(List<E> array, int low, int high) {
  E pivot = array[high];
  int index = low;

  for (int i = low; i < high; i++) {
    E currentItem = array[i];
    E swapItem = array[index];
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

void quickSort<E extends Comparable<E>>(List<E> array, [int? low, int? high]) {
  int length = array.length;
  if (length <= 1) {
    return;
  } else if (length == 2) {
    E i0 = array[0], i1 = array[1];
    if (i1.compareTo(i0) < 0) {
      array[0] = i1;
      array[1] = i0;
    }
    return;
  }

  low = low ?? 0;
  high = high ?? length - 1;

  if ((low >= high) || (low < 0)) {
    return;
  }

  int index = partition(array, low, high);
  if (index <= 0) {
    return;
  }

  quickSort(array, low, index - 1);
  quickSort(array, index + 1, high);
}
