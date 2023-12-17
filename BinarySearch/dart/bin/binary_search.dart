bool binarySearch(List<num> data, num target) {
  int length = data.length;
  if (length <= 0) {
    return false;
  } else if (length == 1) {
    return data[0] == target;
  }
  int midIndex = length ~/ 2;
  num midValue = data[midIndex];
  if (midValue == target) {
    return true;
  }
  if (target < midValue) {
    return binarySearch(data.sublist(0, midIndex), target);
  } else {
    return binarySearch(data.sublist(midIndex), target);
  }
}

void main() {
  List<int> lst = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  print(binarySearch(lst, 3));
  print(binarySearch(lst, 9));
}
