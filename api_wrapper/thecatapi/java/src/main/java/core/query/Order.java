package core.query;

public enum Order {
    ASC("ASC"),
    DESC("DESC"),
    RANDOM("RANDOM");

    private final String order;

    Order(String order) {
        this.order = order;
    }

    public String toString() {
        return order;
    }
}
