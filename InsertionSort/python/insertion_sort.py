import typing


def insertion_sort(array: list[typing.Any], reverse: typing.Optional[bool] = False) -> None:
    """
    Sorts list in ascending or descending order.
    Sorting is done in place.
    Default is ascending order.
    :param reverse: whether to sort in ascending or descending order, true=desc, false=asc

    >>> arr = [5, 2, 1, 5, 8]
    >>> insertion_sort(arr)
    >>> arr
    [1, 2, 5, 5, 8]
    """
    length = len(array)
    if length <= 1:
        return
    elif length == 2:
        item0, item1 = array[0], array[1]
        if (item0 > item1) and (not reverse):
            array[0] = item1
            array[1] = item0
        return

    for i in range(1, length):
        item = array[i]
        for j in range(i, 0, -1):
            prev_item = array[j - 1]
            if prev_item > item:
                array[j] = prev_item
                array[j - 1] = item

    if reverse:
        n = length // 2
        for i in range(n):
            item0 = array[i]
            item1 = array[length - 1 - i]
            array[i] = item1
            array[length - 1 - i] = item0


if __name__ == "__main__":
    arr1: list[int] = [5, 4, 6, 8, 1, 0, 3, 8, 8, 5, 1, 7, 3, 10]
    print(f"Before int: {arr1}")
    insertion_sort(arr1, True)
    print(f"Before int: {arr1}\n")

    arr2: list[float] = [5.3, 4.3, 6.8, 8.14, 1.0, 0.6, 3.8, 8.26, 8.10, 5.39, 1.2, 7.98, 3.982]
    print(f"Before float: {arr2}")
    insertion_sort(arr2)
    print(f"Before float: {arr2}")
