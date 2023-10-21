public class ArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_MULTIPLIER = 2;
    private static final double DEFAULT_LOAD_FACTOR = 0.85;
    private static final double DEFAULT_SHRINK_FACTOR = 0.25;

    private E[] array;
    private int size;
    private int capacity;
    private final int multiplier;
    private final double load_factor;
    private final double shrink_factor;

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity, int multiplier, double load_factor, double shrink_factor) {
        if (load_factor > 1 || shrink_factor > 1)
            throw new IllegalArgumentException("Load factor or shrink factor should be less than or equal to 1");
        this.capacity = capacity;
        this.multiplier = multiplier;
        this.load_factor = load_factor;
        this.shrink_factor = shrink_factor;
        this.size = 0;
        this.array = (E[]) new Object[this.capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY, DEFAULT_MULTIPLIER, DEFAULT_LOAD_FACTOR, DEFAULT_SHRINK_FACTOR);
    }

    private static void checkSize(int index, int size) throws ArrayIndexOutOfBoundsException {
        if (index >= size)
            throw new ArrayIndexOutOfBoundsException();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked") E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];
        }
        this.array = temp;
    }

    private void grow() {
        if (((double) size / array.length) >= load_factor) {
            this.capacity *= multiplier;
            resize(capacity);
        }
    }

    private void shrink() {
        int n = (int) (shrink_factor * array.length);
        if (size < n) {
            resize(n);
            this.capacity = n;
        }
    }

    private void insert(int index, E element) {
        grow();
        if (index == size) {
            array[size] = element;
        } else {
            for (int i = size; i > index; i--) {
                array[i] = array[i - 1];
            }
            array[index] = element;
        }
        size++;
    }

    public E get(int index) {
        checkSize(index, size - 1);
        return array[index];
    }

    public E set(int index, E element) {
        checkSize(index, size - 1);
        E currentElm = array[index];
        array[index] = element;
        return currentElm;
    }

    public void add(int index, E element) {
        checkSize(index, size + 1);
        insert(index, element);
    }

    /**
     * @param element adds element to the end of the list
     */
    public void add(E element) {
        add(size, element);
    }

    public E remove(int index) {
        checkSize(index, size);
        E element = array[index];
        for (int i = index; i < size; i++) {
            if ((i + 1) == size) {
                array[i] = null;
            } else {
                array[i] = array[i + 1];
            }
        }
        size--;
        shrink();
        return element;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < size; i++) {
            builder.append(array[i]);
            if ((i + 1) < size)
                builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(0, 2);
        System.out.println(array.get(0));
        System.out.println(array);
        for (int i = 0; i < 50; i++) {
            array.add(i + 3);
        }
        for (int i = 0; i < 50; i++) {
            array.remove(0);
        }
        System.out.println(array);
    }
}
