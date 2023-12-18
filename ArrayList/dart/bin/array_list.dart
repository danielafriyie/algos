void main() {
  ArrayList<int> array = ArrayList();
  array.add(1);
  array.add(2, 0);
  print(array.get(0));
  print(array);
  for (int i = 0; i < 50; i++) {
    array.add(i + 3);
  }
  print(array);
  for (int i = 0; i < 50; i++) {
    array.remove(0);
  }
  print(array);
  print(array.size);
}

class ArrayList<E> {
  static const int DEFAULT_CAPACITY = 10;
  static const int DEFAULT_MULTIPLIER = 2;
  static const double DEFAULT_LOAD_FACTOR = 0.85;
  static const double DEFAULT_SHRINK_FACTOR = 0.25;

  int _size;
  int _capacity;
  List<E?> _array;
  final int _multiplier;
  final double _load_factor;
  final double _shrink_factor;

  ArrayList(
      {int capacity = ArrayList.DEFAULT_CAPACITY,
      int multiplier = ArrayList.DEFAULT_MULTIPLIER,
      double load_factor = ArrayList.DEFAULT_LOAD_FACTOR,
      double shrink_factor = ArrayList.DEFAULT_SHRINK_FACTOR})
      : _capacity = capacity,
        _multiplier = multiplier,
        _load_factor = load_factor,
        _shrink_factor = shrink_factor,
        _size = 0,
        _array = _create_array(capacity) {
    if (load_factor > 1 || shrink_factor > 1) {
      throw Exception(
          "Load factor or shrink factor should be less than or equal to 1");
    }
  }

  static void _checkSize(int index, int size) {
    if (index >= size) {
      throw IndexError.withLength(index, size);
    }
  }

  int get size => _size;

  bool get isEmpty => _size <= 0;

  static List<T?> _create_array<T>(int capacity) {
    return [for (int i = 0; i < capacity; i++) null];
  }

  void _resize(int capacity) {
    List<E?> temp = _create_array(capacity);
    for (int i = 0; i < _size; i++) {
      temp[i] = _array[i];
    }
    this._array = temp;
  }

  void _grow() {
    if ((_size / _array.length) >= _load_factor) {
      this._capacity *= _multiplier;
      _resize(_capacity);
    }
  }

  void _shrink() {
    int n = (_shrink_factor * _array.length).toInt();
    if (_size < n) {
      this._capacity = n;
      _resize(_capacity);
    }
  }

  void _insert(int index, E element) {
    _grow();
    if (index == size) {
      _array[size] = element;
    } else {
      for (int i = _size; i > index; i--) {
        _array[i] = _array[i - 1];
      }
      _array[index] = element;
    }
    _size++;
  }

  E get(int index) {
    _checkSize(index, size - 1);
    return _array[index]!;
  }

  E set(int index, E element) {
    _checkSize(index, size - 1);
    E currentElm = _array[index]!;
    _array[index] = element;
    return currentElm;
  }

  void add(E element, [int? index]) {
    index = index ?? _size;
    _checkSize(index, size + 1);
    _insert(index, element);
  }

  E remove(int index) {
    _checkSize(index, size);
    E element = _array[index]!;
    for (int i = index; i < _size; i++) {
      if ((i + 1) == _size) {
        _array[i] = null;
      } else {
        _array[i] = _array[i + 1];
      }
    }
    _size--;
    _shrink();
    return element;
  }

  @override
  String toString() {
    return [for (E? elm in _array) if (elm != null) elm].toString();
  }
}
