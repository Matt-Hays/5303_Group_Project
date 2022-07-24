package hotel.reservations.services.reservationDAO;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.reservationDAO.IReservationDAO;
import hotel.reservations.services.invoiceDAO.IInvoiceDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ReservationDAO implements IReservationDAO<Reservation>, IInvoiceDAO<Invoice> {
    private static ReservationDAO dao = null;
    private Database db = null;

    public ReservationDAO(Database db) {
        this.db = db;
    }

     /**
     * Returns a list of reservations that overlap with the requested arrival and departure dates
     * @param arrival arrival date
     * @param departure departure date
     * @return list of reservations
     */

    @Override
    public ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure) {
        return db.getOverlappingReservations(arrival, departure);
    }

    @Override
    public ArrayList<Reservation> findReservations(UUID guestId) {
        return db.getReservationByGuestId(guestId);
    }

    @Override
    public Reservation findReservation(UUID reservationId){
        return db.getReservation(reservationId);
    }

    @Override
    public ArrayList<Reservation> getAllGuestReservations(UUID userId) {
        return db.getReservationByGuestId(userId);
    }

    @Override
    public void updateReservation(Reservation reservation){
        db.updateReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        db.deleteReservation(reservation);
    }

    @Override
    public void cancelReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
        db.updateReservation(reservation);
        // TODO: calculate 80% if need be....
    }

    @Override
    public Invoice generateInvoice(double roomRate, long stayLength) {
        Invoice invoice = new Invoice();
        invoice.setSubtotal(roomRate, stayLength);
        UUID invoiceId = invoice.getInvoiceId();

        db.insertInvoice(invoice);
        return invoice;
    }

}
