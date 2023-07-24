package core.query;

public enum Format implements Query {
    JSON("json"),
    SRC("src");

    private final String format;

    Format(String format) {
        this.format = format;
    }

    @Override
    public String query() {
        return "format=" + format;
    }

    public String toString() {
        return format;
    }
}
