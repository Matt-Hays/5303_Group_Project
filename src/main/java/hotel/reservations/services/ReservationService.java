/**
 * @file ReservationService.java
 * @author Matthew Hays
 * @brief Provides an interface to interact with Reservation domain requests.
 */

package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
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
    public List<Reservation> findReservationByUsername(String username);
    public Reservation findReservationByReservationId(UUID id);
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure);
    public Reservation clerkCreateReservation(String username, Room room, LocalDate arrival, LocalDate departure);
    public Response cancelReservation(Reservation reservation);
    public Response modifyReservation(Reservation modifiedReservation);
    public Invoice getInvoice(UUID invoiceId);
}
