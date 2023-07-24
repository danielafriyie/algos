package core.query;

public enum ImageSize implements Query {
    SMALL("small"),
    MED("med"),
    FULL("full");

    private final String size;

    ImageSize(String size) {
        this.size = size;
    }

    @Override
    public String query() {
        return "size=" + size;
    }

    public String toString() {
        return size;
    }
}
