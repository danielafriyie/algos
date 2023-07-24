package core.query;

public enum ImageSize {
    SMALL("small"),
    MED("med"),
    FULL("full");

    private final String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String toString() {
        return size;
    }
}
