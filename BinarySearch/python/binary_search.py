import typing


def binary_search_recursion(data: list[typing.Union[int, float]], target: typing.Union[int, float]) -> bool:
    length = len(data)
    if length <= 0:
        return False
    elif length == 1:
        return data[0] == target
    mid_index = length // 2
    mid_value = data[mid_index]
    if mid_value == target:
        return True
    if target < mid_value:
        return binary_search_recursion(data[0:mid_index], target)
    else:
        return binary_search_recursion(data[mid_index + 1:], target)


def binary_search_loop(data: list[typing.Union[int, float]], target: typing.Union[int, float]) -> bool:
    low, high = 0, len(data) - 1

    while low <= high:
        mid = (low + high) // 2
        value = data[mid]
        if value == target:
            return True
        elif target < value:
            high = mid - 1
        else:
            low = mid + 1

    return False


if __name__ == "__main__":
    lst = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    print(binary_search_recursion(lst, 3))
    print(binary_search_recursion(lst, 9))
    print(binary_search_loop(lst, 3))
    print(binary_search_loop(lst, 9))
