package model;

import java.util.Map;

public class Model {
    private final int id;
    private final Map<String, Object> data;

    public Model(int id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
