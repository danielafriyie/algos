public class Factorial {

    public static int factorial(int n) throws IllegalArgumentException {
        if (n < 0)
            throw new IllegalArgumentException(String.format("Expects value greater than or equal to zero, got '%s'", n));
        else if (n <= 1)
            return 1;
        else if (n == 2)
            return 2;
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(factorial(1));
        System.out.println(factorial(2));
        System.out.println(factorial(5));
        System.out.println(factorial(10));
    }
}