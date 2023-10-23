import java.util.ArrayList;

public class BinaryTree<E> {

    public static class Node<E> {
        private E element;
        private BinaryTree<E> tree;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element) {
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public BinaryTree<E> getTree() {
            return tree;
        }

        public void setTree(BinaryTree<E> tree) {
            if (this.tree != null)
                throw new IllegalArgumentException("Tree is not empty!");
            this.tree = tree;
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

        public ArrayList<Node<E>> children() {
            ArrayList<Node<E>> list = new ArrayList<>();
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
