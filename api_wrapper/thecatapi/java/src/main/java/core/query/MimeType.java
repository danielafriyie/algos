package core.query;

public enum MimeType implements Query {
    GIF("gif"),
    JPG("jpg"),
    PNG("png");

    private final String type;

    MimeType(String type) {
        this.type = type;
    }

    @Override
    public String query() {
        return "mime_types=" + type;
    }

    public String toString() {
        return type;
    }
}
