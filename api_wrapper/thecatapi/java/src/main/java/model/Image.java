package model;

import java.util.Map;
import java.util.List;

public class Image extends Model {
    private final String url;
    private final String mimeType;
    private final int width;
    private final int height;
    private final List<Breed> breeds;
    private final List<Category> categories;

    public Image(int id, List<Breed> breeds, List<Category> categories, Map<String, Object> data) {
        super(id, data);
        this.url = String.valueOf(data.get("url"));
        this.mimeType = String.valueOf(data.get("mime_type"));
        this.width = (int) data.get("width");
        this.height = (int) data.get("height");
        this.breeds = breeds;
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
