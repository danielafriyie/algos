package model;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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

    public static <T extends Model> T create(Map<String, Object> data, Class<T> klass) throws Exception {
        return klass.getDeclaredConstructor(String.class, Map.class)
                    .newInstance(String.valueOf(data.get("id")), data);
    }

    public static <T extends Model> List<T> createList(List<Map<String, Object>> data, Class<T> klass)
            throws Exception {
        List<T> output = new ArrayList<>();
        if (data == null)
            return output;

        for (Map<String, Object> m : data) {
            output.add(create(m, klass));
        }

        return output;
    }
}
