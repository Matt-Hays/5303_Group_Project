/**
 * @file ReservationDao.java
 * @author Sarah Smallwood
 * @brief Provides an interface for persistence level interactions on the Reservation domain object.
 */

package hotel.reservations.persistence.dao;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface ReservationDao {
    /*
     * Returns a list of reservations that overlap with the requested arrival and departure dates
     * @param arrival arrival date
     * @param departure departure date
     * @return list of reservations
     */
    public ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure);

    public ArrayList<Reservation> findReservations(UUID guestId);

    public Reservation findReservation(UUID reservationId);

    public Response updateReservation(Reservation reservation);

    Response deleteReservation(Reservation reservation);

    Response cancelReservation(Reservation reservation, double roomRate);

    public Invoice generateInvoice(double roomRate, long stayLength);

    public Reservation createReservation(User guest, Room room, LocalDate start, LocalDate end);

    public Invoice getInvoice(UUID invoiceId);

    public Response updateInvoice(Invoice invoice);
}