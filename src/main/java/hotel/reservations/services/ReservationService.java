package hotel.reservations.services;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.persistence.Response;

public interface ReservationService {
    public Response checkIn(Reservation reservation);
    public Response checkOut(Reservation reservation);
}
