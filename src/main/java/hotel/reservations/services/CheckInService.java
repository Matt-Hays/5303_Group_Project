package hotel.reservations.services;

import hotel.reservations.models.reservation.Reservation;

/**
 * Provides an interface for check-in function.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public interface CheckInService {
    Reservation checkIn(Reservation reservation);
    Reservation checkOut(Reservation reservation);
}
