package hotel.reservations.services.reservationDAO;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.invoiceDAO.IInvoiceDAO;
import hotel.reservations.services.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ReservationDAO implements IReservationDAO, IInvoiceDAO {
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

    @Override
    public Invoice getInvoice(UUID invoiceId) {
        return null;
    }

    @Override
    public Invoice createInvoice() {
        return new Invoice();
    }

    @Override
    public Response updateInvoice(Invoice invoice) {
        return null;
    }

    @Override
    public Response deleteInvoice(Invoice invoice) {
        return null;
    }

    @Override
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        Reservation reservation = new Reservation(guest, room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);

        Invoice invoice = createInvoice();
        invoice.setSubtotal(room.getNightlyRate(), reservation.lengthOfStay());
        // TODO: fees?

        Response response = db.insertInvoice(invoice);
        if (Response.FAILURE == response) {
            return null;
        }
        reservation.setInvoiceId(invoice.getInvoiceId());

        response = db.insertReservation(reservation);
        if (Response.FAILURE == response) {
            return null;
        }

        return reservation;
    }
}