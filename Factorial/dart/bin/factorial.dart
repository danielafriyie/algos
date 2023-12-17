int factorial(int n) {
  if (n < 0) {
    throw Exception("Expects value greater than or equal to zero, got '$n'");
  } else if (n <= 1) {
    return 1;
  } else if (n == 2) {
    return 2;
  }
  return n * factorial(n - 1);
}

void main() {
  print(factorial(1));
  print(factorial(2));
  print(factorial(5));
  print(factorial(10));
}
