package hotel.reservations.services.reservationDAO;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface IReservationDAO {
    /*
     * Returns a list of reservations that overlap with the requested arrival and departure dates
     * @param arrival arrival date
     * @param departure departure date
     * @return list of reservations
     */
    public ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure);

    public ArrayList<Reservation> findReservations(UUID guestId);

    public Reservation findReservation(UUID reservationId);
    // what is the difference between findReservation and getReservation (from HotelModels)??

    public ArrayList<Reservation> getAllGuestReservations(UUID userId);

    public void updateReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);

    void cancelReservation(hotel.reservations.models.reservation.Reservation reservation);

    public Invoice generateInvoice(double roomRate, long stayLength);

    public Reservation createReservation(User guest, Room room, LocalDate start, LocalDate end);
}