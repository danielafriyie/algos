import typing


def merge_sort(array: list[typing.Any]) -> list[typing.Any]:
    n = len(array)
    if n <= 1:
        return array
    elif n == 2:
        i0, i1 = array
        if i1 < i0:
            array[0] = i1
            array[1] = i0
        return array

    mid = n // 2
    sorted_left = merge_sort(array[0:mid])
    sorted_right = merge_sort(array[mid:])

    output = []
    i, j = 0, 0
    len_left, len_right = len(sorted_left), len(sorted_right)
    while (i < len_left) and (j < len_right):
        if sorted_left[i] < sorted_right[j]:
            output.append(sorted_left[i])
            i += 1
        else:
            output.append(sorted_right[j])
            j += 1

    output.extend(sorted_left[i:])
    output.extend(sorted_right[j:])

    return output


if __name__ == "__main__":
    print(merge_sort([12, 11, 13, 5, 6, 7]))
    print(merge_sort([92, 0, 3, 6, 1, 5, 8, 45, 75, 12, 98, 3, 65]))
