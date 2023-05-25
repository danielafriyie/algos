import typing

E = typing.TypeVar("E")


def snail_sort(data: list[list[E]]) -> list[E]:
    if len(data[0]) <= 0:
        return []

    data_len, output = len(data), []

    for _ in range(data_len):
        if len(data) < 1:
            break
        output.extend(data.pop(0))
        for d in data:
            output.append(d.pop(-1))
        if len(data) > 0:
            output.extend(data.pop(-1)[::-1])
            for d in data[::-1]:
                output.append(d.pop(0))

    return output


if __name__ == "__main__":
    array1 = [[1, 2, 3],
              [4, 5, 6],
              [7, 8, 9]]
    print(snail_sort(array1))
    array2 = [[1, 2, 3],
              [8, 9, 4],
              [7, 6, 5]]
    print(snail_sort(array2))
    arr3 = [[1, 2, 3, 4, 5],
            [6, 7, 8, 9, 10],
            [11, 12, 13, 14, 15],
            [16, 17, 18, 19, 20],
            [21, 22, 23, 24, 25]]
    print(snail_sort(arr3))
