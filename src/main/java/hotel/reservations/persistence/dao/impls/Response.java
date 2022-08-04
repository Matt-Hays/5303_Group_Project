package hotel.reservations.persistence.dao.impls;

public enum Response {
    SUCCESS (0),
    FAILURE (-1);

    private final int value;

    Response(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
