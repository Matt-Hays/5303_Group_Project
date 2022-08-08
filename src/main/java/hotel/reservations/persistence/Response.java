/**
 * @file Response.java
 * @author Joshua Wellman
 * @brief Provides an enum to indicate a success or failure upon execution of an action.
 */

package hotel.reservations.persistence;

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
