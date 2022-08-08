/**
 * @file ReservationStatus.java
 * @author Joshua Wellman
 * @brief Provides the application with an enum type that represents the state of a given reservation.
 */

package hotel.reservations.models.reservation;

public enum ReservationStatus {
    AWAITING,
    CHECKEDIN,
    CANCELLED,
    COMPLETE
}
