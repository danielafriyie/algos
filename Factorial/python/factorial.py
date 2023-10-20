def factorial(n: int) -> int:
    if n < 0:
        raise ValueError(f"Expects value greater than or equal to zero, got '{n}'")
    elif n <= 1:
        return 1
    elif n == 2:
        return 2
    return n * factorial(n - 1)


if __name__ == "__main__":
    print(factorial(1))
    print(factorial(2))
    print(factorial(5))
    print(factorial(10))
