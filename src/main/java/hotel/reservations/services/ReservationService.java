package hotel.reservations.services;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    public Response checkIn(Reservation reservation);
    public Response checkOut(Reservation reservation);
    public List<Reservation> findReservationByUserId(UUID id);
    public Reservation findReservationByReservationId(UUID id);
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure);
    public Response cancelReservation(Reservation reservation);
    public Response modifyReservation(Reservation modifiedReservation);
}
