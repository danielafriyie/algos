#include <stdio.h>

int factorial(int n) {
    if (n < 0)
        return -1;
    else if (n == 1)
        return 1;
    else if (n == 2)
        return 2;
    return n * factorial(n - 1);
}

int main() {
    printf("%d\n", factorial(1));
    printf("%d\n", factorial(2));
    printf("%d\n", factorial(5));
    printf("%d\n", factorial(10));

    return 0;
}
