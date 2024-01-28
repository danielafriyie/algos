public class Main {

    public static void test1() throws Exception {
        System.out.println("Test 1");
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.insert(5, 5);
        queue.insert(3, 3);
        queue.insert(4, 4);
        queue.insert(2, 2);
        queue.insert(6, 6);
        queue.insert(1, 1);
        System.out.println(queue.size());
        System.out.println(queue.removeMin());
        System.out.println(queue.size());
        System.out.println(queue);
        System.out.println("End of Test 1");
    }

    public static void test2() throws Exception {
        System.out.println("Test 2");
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 1; i < 51; i++) {
            queue.insert(i, i);
        }
        for (var node : queue) {
            System.out.println(node);
        }
        System.out.println(queue);
        System.out.println("End of Test 2");
    }

    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }
}
