import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.function.Function;

public class BinaryExpressionTree {
    private final String expression;
    private int index;
    private Node<String> root;

    private static final List<String> OPERATORS = Arrays.asList("+", "-", "/", "*");

    public BinaryExpressionTree(String expression) {
        this.expression = expression.replaceAll(" ", "");
        this.index = 0;
        this.root = null;
        this.validate();
    }

    public Node<String> getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public static <T> boolean isLeaf(Node<T> node) {
        return !node.hasChildren();
    }

    @SuppressWarnings("all")
    public List<List<Node<String>>> getLayers() {
        if (isEmpty())
            return new ArrayList<>();
        List<List<Node<String>>> output = new ArrayList<>();
        output.add(Arrays.asList(root));
        if (isLeaf(root)) {
            return output;
        }
        List<Node<String>> children = root.children();
        while (!children.isEmpty()) {
            output.add(children);
            List<Node<String>> temp = new ArrayList<>();
            for (Node<String> child : children) {
                temp.addAll(child.children());
            }
            children = temp;
        }
        return output;
    }

    public int depth() {
        return getLayers().size();
    }

    public int height() {
        List<List<Node<String>>> layers = getLayers();
        int h = 0;
        for (List<Node<String>> list : layers) {
            h += list.size();
        }
        return h;
    }

    public int size() {
        return height();
    }

    private void validate() {
        long openB = expression.chars().filter(c -> c == '(').count();
        long closeB = expression.chars().filter(c -> c == ')').count();
        if (openB != closeB)
            throw new IllegalArgumentException("Invalid Expression!");
    }

    private String peek(int index) {
        return String.valueOf(expression.charAt(this.index + index));
    }

    private Node<String> createLeft() {
        if (peek(0).equals("(")) {
            this.index += 1;
            return createRoot(")");
        }

        List<String> list = new ArrayList<>();
        while (true) {
            try {
                String val = String.valueOf(expression.charAt(index));
                if (OPERATORS.contains(val))
                    break;
                list.add(val);
                this.index += 1;
            } catch (IndexOutOfBoundsException ignore) {
                break;
            }
        }

        return new Node<>(String.join("", list));
    }

    private Node<String> createRight(String delimiter) {
        int idx = index;
        List<String> list = new ArrayList<>();

        while (true) {
            try {
                String val = String.valueOf(expression.charAt(idx));
                if (val.equals("(")) {
                    this.index += 1;
                    return createRoot(")");
                } else if (val.equals(delimiter)) {
                    idx += 1;
                    break;
                } else if (OPERATORS.contains(val)) {
                    return createRoot(delimiter);
                }
                list.add(val);
                idx += 1;
            } catch (IndexOutOfBoundsException ignore) {
                break;
            }
        }

        this.index = idx;
        return new Node<>(String.join("", list));
    }

    private String getOperator() {
        String opr = String.valueOf(expression.charAt(index));
        this.index += 1;
        return opr;
    }

    private Node<String> createRoot(String delimiter) {
        Node<String> left = createLeft();
        Node<String> root = new Node<>(getOperator());
        Node<String> right = createRight(delimiter);
        root.setLeft(left);
        root.setRight(right);
        return root;
    }

    public void parse() {
        this.root = createRoot(null);
    }

    public void visualizeLeftToRight(Node<String> node, int level) {
        if (isEmpty() || node == null)
            return;
        if (node.getRight() != null)
            visualizeLeftToRight(node.getRight(), level + 2);
        System.out.println(" ".repeat(4).repeat(level) + "-> " + node.getElement());
        if (node.getLeft() != null)
            visualizeLeftToRight(node.getLeft(), level + 2);
    }

    public void visualizeLeftToRight() {
        visualizeLeftToRight(root, 0);
    }

    private void vis(Node<String> node, int depth, int start, List<String> list) {
        String val = node.getElement();
        int col = depth * depth;
        String nStr = " ".repeat(col) + " ".repeat(start) + val;
        list.set(node.getIndex(), nStr);
        if (node.getLeft() != null) {
            vis(node.getLeft(), depth - 1, start, list);
            if (node.getRight() != null) {
                vis(node.getRight(), depth - 1, col + start, list);
            }
        }
    }

    public String visualizeTopToBottom(Node<String> node, int depth, int start) {
        Function<List<List<Node<String>>>, Integer> createIndex = (List<List<Node<String>>> input) -> {
            List<Node<String>> flattened = new ArrayList<>();
            int counter = 0;
            for (List<Node<String>> list : input) {
                for (Node<String> n : list) {
                    n.setIndex(counter);
                    flattened.add(n);
                    counter += 1;
                }
            }
            return flattened.size();
        };

        List<List<Node<String>>> layers = getLayers();
        List<String> results = new ArrayList<>(Collections.nCopies(createIndex.apply(layers), ""));;
        vis(node, depth, start, results);
        List<String> output = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            int n = layers.get(i).size();
            List<String> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                try {
                    String val = results.remove(0);
                    String[] split = val.split(" ");
                    int div = String.join("", list).length();
                    list.add(String.join(" ", Arrays.copyOfRange(split, div, split.length)));
                } catch (IndexOutOfBoundsException ignore) {
                    break;
                }
            }
            output.add(String.join("", list));
        }
        return String.join("\n\n", output);
    }

    public String visualizeTopToBottom() {
        if (isEmpty())
            return "";
        return visualizeTopToBottom(root, depth(), 0);
    }

    public List<Node<String>> inOrderTraversal(Node<String> node) {
        List<Node<String>> output = new ArrayList<>();
        if (isEmpty() || node == null)
            return output;
        if (node.getLeft() != null) {
            output.addAll(inOrderTraversal(node.getLeft()));
        }
        output.add(node);
        if (node.getRight() != null) {
            output.addAll(inOrderTraversal(node.getRight()));
        }
        return output;
    }

    public List<Node<String>> inOrderTraversal() {
        return inOrderTraversal(root);
    }

    public List<Node<String>> preOrderTraversal(Node<String> node) {
        List<Node<String>> output = new ArrayList<>();
        if (isEmpty() || node == null)
            return output;
        output.add(node);
        List<Node<String>> children = node.children();
        for (Node<String> child : children) {
            output.addAll(preOrderTraversal(child));
        }
        return output;
    }

    public List<Node<String>> preOrderTraversal() {
        return preOrderTraversal(root);
    }

    public List<Node<String>> postOrderTraversal(Node<String> node) {
        List<Node<String>> output = new ArrayList<>();
        if (isEmpty() || node == null)
            return output;
        if (node.getLeft() != null)
            output.addAll(postOrderTraversal(node.getLeft()));
        if (node.getRight() != null)
            output.addAll(postOrderTraversal(node.getRight()));
        output.add(node);
        return output;
    }

    public List<Node<String>> postOrderTraversal() {
        return postOrderTraversal(root);
    }

    public List<Node<String>> breathFirstTraversal() {
        List<List<Node<String>>> layers = getLayers();
        List<Node<String>> output = new ArrayList<>();
        for (List<Node<String>> list : layers) {
            output.addAll(list);
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println("\nEnter expression, eg: (2 + 3)");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\n>>> ");
            String query = scanner.nextLine();
            if (query.strip().equalsIgnoreCase("q"))
                break;
            try {
                BinaryExpressionTree expTree = new BinaryExpressionTree(query);
                expTree.parse();
                System.out.println();
                System.out.println(expTree.visualizeTopToBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Node<E> {
        private final E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private int index;

        private Node(E element) {
            this.element = element;
            this.parent = null;
            this.left = null;
            this.right = null;
            this.index = -1;  // only computed when generating tree visualization
        }

        public E getElement() {
            return element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            if (this.parent != null)
                throw new IllegalArgumentException("Parent is not empty!");
            this.parent = parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            if (this.left != null)
                throw new IllegalArgumentException("Left is not empty!");
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            if (this.right != null)
                throw new IllegalArgumentException("Right is not empty!");
            if (this.left == null)
                throw new IllegalArgumentException("Left is empty!");
            this.right = right;
        }

        public List<Node<E>> children() {
            List<Node<E>> list = new ArrayList<>();
            if (left != null) {
                list.add(left);
                if (right != null) {
                    list.add(right);
                }
            }
            return list;
        }

        public boolean hasChildren() {
            return !children().isEmpty();
        }

        public String toString() {
            return String.format("%s(%s)", getClass().getName(), element);
        }
    }
}
