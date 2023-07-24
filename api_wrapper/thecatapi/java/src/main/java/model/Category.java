package model;

import java.util.Map;

public class Category extends Model {
    private final String name;

    public Category(String id, Map<String, Object> data) {
        super(id, data);
        this.name = String.valueOf(data.get("name"));
    }

    public String getName() {
        return name;
    }
}
