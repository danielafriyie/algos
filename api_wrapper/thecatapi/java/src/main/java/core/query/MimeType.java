package core.query;

public enum MimeType {
    GIF("gif"),
    JPG("jpg"),
    PNG("png");

    private final String type;

    MimeType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
