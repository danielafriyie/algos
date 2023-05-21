import typing

Number = typing.Union[int, float]


def bubble_sort(data: list[Number]) -> None:
    length = len(data)

    for i in range(length - 1):
        for j in range(i + 1, length):
            value, next_ = data[i], data[j]
            if value > next_:
                data[j] = value
                data[i] = next_


if __name__ == "__main__":
    lst = [1, 3, 1, 5, 3, 6, 9, 10, 2, 7, 99, 32, 5, 2, 676, 98, 0, 456, 0, 234, 12, 567, 6, 34, 667, 2]
    bubble_sort(lst)
    print(lst)
    lst2 = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    bubble_sort(lst2)
    print(lst2)
