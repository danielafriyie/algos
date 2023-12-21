void main() {
  print(mergeSort<num>([12, 11, 13, 5, 6, 7]));
  print(mergeSort<num>([92, 0, 3, 6, 1, 5, 8, 45, 75, 12, 98, 3, 65]));
}

List<E> mergeSort<E extends Comparable<E>>(List<E> array) {
  int length = array.length;
  if (length <= 1) {
    return array;
  } else if (length == 2) {
    E i0 = array[0];
    E i1 = array[1];
    if (i1.compareTo(i0) < 0) {
      array[0] = i1;
      array[1] = i0;
      return array;
    }
  }

  int mid = length ~/ 2;
  List<E> sortedLeft = mergeSort(array.sublist(0, mid));
  List<E> sortedRight = mergeSort(array.sublist(mid, length));

  List<E> output = [];
  int i = 0;
  int j = 0;
  int lenLeft = sortedLeft.length;
  int lenRight = sortedRight.length;
  while ((i < lenLeft) && (j < lenRight)) {
    if (sortedLeft[i].compareTo(sortedRight[j]) < 0) {
      output.add(sortedLeft[i]);
      i++;
    } else {
      output.add(sortedRight[j]);
      j++;
    }
  }

  output.addAll(sortedLeft.sublist(i, sortedLeft.length));
  output.addAll(sortedRight.sublist(j, sortedRight.length));

  return output;
}
