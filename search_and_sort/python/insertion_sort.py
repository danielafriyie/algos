import typing

Number = typing.Union[int, float]


def insertion_sort(data: list[Number]) -> None:
    n = len(data)
    for i in range(1, n):
        value = data[i]
        pos = i
        while pos > 0 and value < data[pos - 1]:
            data[pos] = data[pos - 1]
            pos -= 1
            data[pos] = value


if __name__ == "__main__":
    lst = [1, 3, 1, 5, 3, 2, 7, 99, 32, 5, 2, 676, 98, 0, 456, 0, 234, 12, 567, 6, 34, 667, 2]
    insertion_sort(lst)
    print(lst)
    lst2 = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    insertion_sort(lst2)
    print(lst2)
