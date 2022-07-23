package hotel.reservations.services.reservationDAO;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.persistence.Database;
import hotel.reservations.models.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class reservationDAO implements IReservationDAO {
    private static reservationDAO dao = null;
    private static Database db = null;

    private reservationDAO() {
    }
    public static reservationDAO getReservationDAO() {
        if (null == dao) {
            dao = new reservationDAO();
            db = Database.Database();
        }

        return dao;
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
    public void updateReservation(Reservation reservation){
        db.updateReservation(reservation);
    }

    @Override
    public void deleteReservation() {
        db.deleteReservation(this);
    }

    @Override
    public void cancelReservation() {
        status = ReservationStatus.CANCELLED;
        db.updateReservation(this);
        // TODO: calculate 80% if need be....
    }

    @Override
    Invoice generateInvoice(double roomRate, long stayLength) {
        Invoice invoice = new Invoice();
        invoice.setSubtotal(roomRate, stayLength);
        this.invoiceId = invoice.getInvoiceId();

        db.insertInvoice(invoice);
        return invoice;
    }

}
