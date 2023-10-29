import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class HashMap<K, V> {
    private int size;
    private int capacity;
    private final double loadFactor;
    private List<List<Pair<K, V>>> buckets;
    private static final int multiplier = 2;

    private static final int DEFAULT_CAPACITY = 50;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    public HashMap(int capacity, double loadFactor) {
        if (capacity < 1)
            throw new IllegalArgumentException("Capacity must be a positive integer!");
        if (loadFactor > 1 || loadFactor < 0)
            throw new IllegalArgumentException("Load factor should be (0 < loadFactor <= 1)");

        this.size = 0;
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.buckets = createBuckets(capacity);
    }

    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(HashMap<K, V> hashMap, int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
        Set<Pair<K, V>> entries = hashMap.entrySet();
        for (Pair<K, V> pair : entries) {
            put(pair.getKey(), pair.getValue());
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size < 1;
    }

    public Set<K> keySet() {
        Set<K> output = new HashSet<>();
        for (List<Pair<K, V>> bucket : buckets) {
            for (Pair<K, V> pair : bucket) {
                output.add(pair.getKey());
            }
        }
        return output;
    }

    public List<V> values() {
        List<V> output = new ArrayList<>();
        for (List<Pair<K, V>> bucket : buckets) {
            for (Pair<K, V> pair : bucket) {
                output.add(pair.getValue());
            }
        }
        return output;
    }

    public Set<Pair<K, V>> entrySet() {
        Set<Pair<K, V>> output = new HashSet<>();
        for (List<Pair<K, V>> bucket : buckets) {
            output.addAll(bucket);
        }
        return output;
    }

    public V get(K key) {
        List<Pair<K, V>> bucket = getBucket(key);
        for (Pair<K, V> pair : bucket) {
            if (pair.getKey() == key)
                return pair.getValue();
        }
        return null;
    }

    public void put(K key, V value) {
        if (loadFactorExceeded())
            resizeAndRehash();
        boolean found = false;
        List<Pair<K, V>> bucket = getBucket(key);
        for (Pair<K, V> pair : bucket) {
            if (pair.getKey() == key) {
                pair.setValue(value);
                found = true;
                break;
            }
        }
        if (!found) {
            bucket.add(new Pair<>(key, value));
            size++;
        }
    }

    public V remove(K key) {
        V value = null;
        int index = -1;
        List<Pair<K, V>> bucket = getBucket(key);
        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> pair = bucket.get(i);
            if (pair.getKey() == key) {
                value = pair.getValue();
                index = i;
                break;
            }
        }
        if (index >= 0) {
            bucket.remove(index);
            size--;
        }
        return value;
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    private List<List<Pair<K, V>>> createBuckets(int capacity) {
        List<List<Pair<K, V>>> buckets = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            buckets.add(new ArrayList<>());
        }
        return buckets;
    }

    private List<Pair<K, V>> getBucket(K key) {
        return buckets.get(hash(key));
    }

    private boolean loadFactorExceeded() {
        return ((double) size / capacity) >= loadFactor;
    }

    private void resizeAndRehash() {
        this.capacity *= multiplier;
        this.buckets = new HashMap<>(this, this.capacity).buckets;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("hey", 23);
        hm.put("five", 5);
        hm.put("easy", 0);
        hm.put("difficult", 100);
        System.out.println(hm.get("five"));
        System.out.println(hm.keySet());
        System.out.println(hm.values());
        System.out.println(hm.entrySet());
    }

    public static class Pair<K, V> {
        private final K key;
        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        private void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return key.hashCode() + value.hashCode();
        }

        public boolean equals(Pair<K, V> other) {
            return (key == other.key) && (value == other.value);
        }

        @Override
        public String toString() {
            return String.format("%s(key=%s, value=%s)", getClass().getName(), key, value);
        }
    }
}
