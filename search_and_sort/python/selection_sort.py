import typing

Number = typing.Union[int, float]


def selection_sort(data: list[Number]) -> None:
    length = len(data)
    for i in range(length):
        min_index = i
        for j in range(i + 1, length):
            if data[j] < data[min_index]:
                min_index = j

        if min_index != i:
            i_value, j_vlaue = data[i], data[min_index]
            data[i] = j_vlaue
            data[min_index] = i_value


if __name__ == "__main__":
    lst = [1, 3, 1, 5, 3, 2, 7, 99, 32, 5, 2, 676, 98, 0, 456, 0, 234, 12, 567, 6, 34, 667, 2]
    selection_sort(lst)
    print(lst)
    lst2 = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    selection_sort(lst2)
    print(lst2)
