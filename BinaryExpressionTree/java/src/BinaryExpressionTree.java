import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class BinaryExpressionTree {
    private final String expression;
    private int index;
    private final int length;
    private Node<String> root;

    public BinaryExpressionTree(String expression) {
        this.expression = expression;
        this.index = 0;
        this.length = this.expression.length();
        this.root = null;
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
