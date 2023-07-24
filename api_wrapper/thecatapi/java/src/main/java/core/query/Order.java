package core.query;

public enum Order implements Query {
    ASC("ASC"),
    DESC("DESC"),
    RANDOM("RANDOM");

    private final String order;

    Order(String order) {
        this.order = order;
    }

    @Override
    public String query() {
        return "order=" + order;
    }

    public String toString() {
        return order;
    }
}
