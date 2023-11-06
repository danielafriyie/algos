import typing


def partition(array: list[typing.Any], low: int, high: int) -> int:
    pivot = array[high]
    index = low
    for i in range(low, high):
        current_item, swap_item = array[i], array[index]
        if current_item <= pivot:
            array[i] = swap_item
            array[index] = current_item
            index += 1

    if index > 0:
        array[index], array[high] = array[high], array[index]

    return index


def quick_sort(array: list[typing.Any], low: typing.Optional[int] = None, high: typing.Optional[int] = None) -> None:
    n = len(array)
    if n <= 1:
        return
    elif n == 2:
        i0, i1 = array
        if i1 < i0:
            array[0] = i1
            array[1] = i0
        return

    if low is None:
        low = 0
    if high is None:
        high = n - 1

    if (low >= high) or (low < 0):
        return

    index = partition(array, low, high)
    if index <= 0:
        return

    quick_sort(array, low, index - 1)
    quick_sort(array, index + 1, high)


if __name__ == "__main__":
    lsts = [
        [1, 2, 3, 4, 5],
        [10, 7, 8, 9, 1, 5],
        [3, 6, 21, 3, 5, 7, 676, 98, 67, 346, 6, 5, 4, 8, 0, 2, 4, 2, 7, 3, 5, 25],
    ]
    for lst in lsts:
        print(lst)
        quick_sort(lst)
        print(lst)
        print()
