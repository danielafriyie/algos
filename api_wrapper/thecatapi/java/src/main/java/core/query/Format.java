package core.query;

public enum Format {
    JSON("json"),
    SRC("src");

    private final String format;

    Format(String format) {
        this.format = format;
    }

    public String toString() {
        return format;
    }
}
