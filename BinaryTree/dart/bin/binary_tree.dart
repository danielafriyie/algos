import "dart:math" as math;

void main() {
  BinaryTree<int> btree = BinaryTree(Node(0));
  btree.addLeft(btree.root!, 1);
  btree.addRight(btree.root!, 2);

  btree.addLeft(btree.root!.left!, 3);
  btree.addRight(btree.root!.left!, 4);

  btree.addLeft(btree.root!.right!, 5);
  btree.addRight(btree.root!.right!, 6);

  btree.addLeft(btree.root!.left!.left!, 7);
  print(btree.depth);
  print(btree.size);
  print(btree.inorderTraversal());
  print(btree.preorderTraversal());
  print(btree.postorderTraversal());
  print(btree.breathFirstTraversal());
  print(btree);
}

class Node<E> {
  E _element;
  BinaryTree<E>? _tree;
  Node<E>? _parent;
  Node<E>? _left;
  Node<E>? _right;

  Node(this._element, [BinaryTree<E>? tree, Node<E>? parent, Node<E>? left, Node<E>? right]) {
    this._tree = tree;
    this._parent = parent;
    this._left = left;
    this._right = right;
  }

  E get element => _element;

  Node<E>? get right => _right;

  set right(Node<E>? node) {
    this._right = node;
  }

  Node<E>? get left => _left;

  set left(Node<E>? node) {
    this._left = node;
  }

  Node<E>? get parent => _parent;

  set parent(Node<E>? node) {
    this._parent = node;
  }

  BinaryTree<E>? get tree => _tree;

  set tree(BinaryTree<E>? tree) {
    if (_tree != null) {
      throw Exception("Tree is not empty!");
    }
    this._tree = tree;
  }

  List<Node<E>> children() {
    List<Node<E>> output = [];
    if (_left != null) {
      output.add(_left!);
      if (_right != null) {
        output.add(_right!);
      }
    }
    return output;
  }

  bool hasChildren() {
    return children().length > 0;
  }

  @override
  String toString() {
    return "Node($_element)";
  }
}

class BinaryTree<E> extends Iterable<Node<E>> {
  Node<E>? _root;

  BinaryTree([Node<E>? root]) {
    root?.tree = this;
    this._root = root;
  }

  int get depth => getLayers(_root).length;

  int get height {
    List<List<Node<E>>> layers = getLayers(_root);
    int value = 0;
    for (List<Node<E>> list in layers) {
      value += list.length;
    }
    return value;
  }

  int get size => height;

  bool get isEmpty => _root == null;

  Node<E>? get root => _root;

  void _validate(Node<E> node) {
    if (node.tree != this) {
      throw Exception("Node does not belong to this tree!");
    }
  }

  Node<E> addLeft(Node<E> parent, E element) {
    _validate(parent);
    if (parent.left != null) {
      throw Exception("Parent's left is not empty!");
    }

    Node<E> node = Node(element, this, parent);
    parent.left = node;
    return node;
  }

  Node<E> addRight(Node<E> parent, E element) {
    _validate(parent);
    if (parent.right != null) {
      throw Exception("Parent's right is not empty!");
    }

    Node<E> node = Node(element, this, parent);
    parent.right = node;
    return node;
  }

  bool isRoot(Node<E> node) => (node == _root) && (_root != null);

  bool isLeaf(Node<E> node) => !node.hasChildren();

  int nodeHeight(Node<E>? node) {
    if (node == null)
      return 0;
    else if (isLeaf(node)) return 1;
    return 1 + math.max(nodeHeight(node.left), nodeHeight(node.right));
  }

  List<List<Node<E>>> getLayers(Node<E>? node) {
    if (node == null)
      return [];
    else if (isLeaf(node))
      return [
        [node]
      ];

    List<List<Node<E>>> output = [
      [node]
    ];
    List<Node<E>> children = node.children();
    while (children.length > 0) {
      output.add(children);
      List<Node<E>> childrenTemp = [];
      for (Node<E> c in children) {
        childrenTemp.addAll(c.children());
      }

      children = childrenTemp;
    }
    return output;
  }

  List<Node<E>> preorderTraversal([Node<E>? node]) {
    if (isEmpty) return [];

    node = (node ?? _root)!;
    List<Node<E>> output = [node];
    List<Node<E>> children = node.children();
    for (Node<E> child in children) {
      output.addAll(preorderTraversal(child));
    }

    return output;
  }

  List<Node<E>> postorderTraversal([Node<E>? node]) {
    if (isEmpty) return [];
    node = (node ?? _root)!;
    if (isLeaf(node)) return [node];

    List<Node<E>> output = [];
    if (node.left != null) output.addAll(postorderTraversal(node.left));
    if (node.right != null) output.addAll(postorderTraversal(node.right));
    output.add(node);
    return output;
  }

  List<Node<E>> inorderTraversal([Node<E>? node]) {
    if (isEmpty) return [];
    node = (node ?? _root)!;
    List<Node<E>> output = [];
    if (node.left != null) output.addAll(postorderTraversal(node.left));
    output.add(node);
    if (node.right != null) output.addAll(postorderTraversal(node.right));
    return output;
  }

  List<Node<E>> breathFirstTraversal() {
    List<List<Node<E>>> layers = getLayers(_root);
    return [for (List<Node<E>> list in layers) ...list];
  }

  String visualizeLeftToRight(Node<E>? node, int level) {
    if (node == null) return "";

    List<String> _vis(Node<E> n, int lvl) {
      List<String> output = [];
      if (n.right != null) {
        output.addAll(_vis(n.right!, lvl + 2));
      }
      output.add("""${((" " * 4) * lvl)}-> ${n.element}""");
      if (n.left != null) {
        output.addAll(_vis(n.left!, lvl + 2));
      }

      return output;
    }

    List<String> results = _vis(node, level);
    results.insert(0, "\n");
    results.add("\n");
    return results.join("\n");
  }

  @override
  String toString() => visualizeLeftToRight(_root, depth);

  @override
  Iterator<Node<E>> get iterator => breathFirstTraversal().iterator;
}
