import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class BinaryTree<E> {
    private Node<E> root;

    public BinaryTree(Node<E> root) {
        this.root = root;
        this.root.setTree(this);
    }

    public BinaryTree() {
        this.root = null;
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> node) {
        this.root = node;
    }

    private void validate(Node<E> node) {
        if (node.getTree() != this)
            throw new IllegalArgumentException("Node does not belong to this tree!");
    }

    public Node<E> addLeft(Node<E> parent, E element) {
        validate(parent);
        Node<E> node = new Node<>(element);
        node.setTree(this);
        node.setParent(parent);
        parent.setLeft(node);
        return node;
    }

    public Node<E> addRight(Node<E> parent, E element) {
        validate(parent);
        Node<E> node = new Node<>(element);
        node.setTree(this);
        node.setParent(parent);
        parent.setRight(node);
        return node;
    }

    public boolean isRoot(Node<E> node) {
        return node == this.root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public static <T> boolean isLeaf(Node<T> node) {
        return !node.hasChildren();
    }

    @SuppressWarnings("all")
    public List<List<Node<E>>> getLayers() {
        if (isEmpty())
            return new ArrayList<>();
        List<List<Node<E>>> output = new ArrayList<>();
        output.add(Arrays.asList(root));
        if (isLeaf(root)) {
            return output;
        }
        List<Node<E>> children = root.children();
        while (!children.isEmpty()) {
            output.add(children);
            List<Node<E>> temp = new ArrayList<>();
            for (Node<E> child : children) {
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
        List<List<Node<E>>> layers = getLayers();
        int h = 0;
        for (List<Node<E>> list : layers) {
            h += list.size();
        }
        return h;
    }

    public int size() {
        return height();
    }

    public void visualize(Node<E> node, int level) {
        if (isEmpty() || node == null)
            return;
        if (node.getRight() != null)
            visualize(node.getRight(), level + 2);
        System.out.println(" ".repeat(4).repeat(level) + "-> " + node.getElement());
        if (node.getLeft() != null)
            visualize(node.getLeft(), level + 2);
    }

    public void visualize() {
        visualize(root, 0);
    }

    public List<Node<E>> inOrderTraversal(Node<E> node) {
        List<Node<E>> output = new ArrayList<>();
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

    public List<Node<E>> inOrderTraversal() {
        return inOrderTraversal(root);
    }

    public List<Node<E>> preOrderTraversal(Node<E> node) {
        List<Node<E>> output = new ArrayList<>();
        if (isEmpty() || node == null)
            return output;
        output.add(node);
        List<Node<E>> children = node.children();
        for (Node<E> child : children) {
            output.addAll(preOrderTraversal(child));
        }
        return output;
    }

    public List<Node<E>> preOrderTraversal() {
        return preOrderTraversal(root);
    }

    public List<Node<E>> postOrderTraversal(Node<E> node) {
        List<Node<E>> output = new ArrayList<>();
        if (isEmpty() || node == null)
            return output;
        if (node.getLeft() != null)
            output.addAll(postOrderTraversal(node.getLeft()));
        if (node.getRight() != null)
            output.addAll(postOrderTraversal(node.getRight()));
        output.add(node);
        return output;
    }

    public List<Node<E>> postOrderTraversal() {
        return postOrderTraversal(root);
    }

    public List<Node<E>> breathFirstTraversal() {
        List<List<Node<E>>> layers = getLayers();
        List<Node<E>>  output = new ArrayList<>();
        for (List<Node<E>> list : layers) {
            output.addAll(list);
        }
        return output;
    }

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

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>(new Node<>(0));
        tree.addLeft(tree.root, 1);
        tree.addRight(tree.root, 2);

        tree.addLeft(tree.root.getLeft(), 3);
        tree.addRight(tree.root.getLeft(), 4);

        tree.addLeft(tree.root.getRight(), 5);
        tree.addRight(tree.root.getRight(), 6);

        tree.addLeft(tree.root.getLeft().getLeft(), 7);

        System.out.println(tree.depth());
        System.out.println(tree.height());
        System.out.println(tree.inOrderTraversal());
        System.out.println(tree.preOrderTraversal());
        System.out.println(tree.postOrderTraversal());
        System.out.println(tree.breathFirstTraversal());
        System.out.println();
        tree.visualize();
    }
}
