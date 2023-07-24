package model;

import java.util.Map;

public class Model {
    private final String id;
    private final Map<String, Object> data;

    public Model(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public <T> T get(String key, Class<T> cast) {
        return cast.cast(data.get(key));
    }

    public Object get(String key) {
        return data.get(key);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
