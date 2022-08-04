package hotel.reservations.persistence.dao.impls;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.InvoiceDao;
import hotel.reservations.persistence.dao.ReservationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ReservationDaoImpl implements ReservationDao, InvoiceDao {
    private DatabaseImpl db = null;

    public ReservationDaoImpl(DatabaseImpl db) {
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
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        ResultSet rs = db.getOverlappingReservations(arrival, departure);
        if (rs == null) {
            return reservations;
        }

        try {
            while(rs.next()) {
                reservations.add(createReservation(rs));
            }
        } catch (SQLException e) {
        }

        return reservations;

    }

    @Override
    public ArrayList<Reservation> findReservations(UUID guestId) {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        ResultSet rs = db.getReservationByGuestId(guestId);
        if (rs == null) {
            return reservations;
        }

        try {
            while(rs.next()) {
                reservations.add(createReservation(rs));
            }
        } catch (SQLException e) {
        }

        return reservations;
    }

    @Override
    public Reservation findReservation(UUID reservationId){
        ResultSet rs =  db.getReservationByReservationId(reservationId);
        return createReservation(rs);
    }

    /**
     * create a reservation object from resultset
     * @param rs
     * @return reservation object or null on error
     */
    private Reservation createReservation(ResultSet rs) {
        if (null == rs) {
            return null;
        }

        try {
            UUID reservationId = UUID.fromString(rs.getString("id"));
            UUID customerId = UUID.fromString(rs.getString("customerId"));
            UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
            int roomId = rs.getInt("roomId");
            LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
            LocalDate arrival = LocalDate.parse(rs.getString("arrival"));
            LocalDate departure = LocalDate.parse(rs.getString("departure"));
            ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

            return new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status);
        } catch (SQLException e) {
        }

        return null;
    }

    @Override
    public Response updateReservation(Reservation reservation){
        return db.updateReservation(reservation.getReservationId(), reservation.getCustomerId(),
            reservation.getInvoiceId(), reservation.getRoomNumber(), reservation.getCreatedAt(),
            reservation.getArrival(), reservation.getDeparture(), reservation.getStatus());
    }

    @Override
    public Response deleteReservation(Reservation reservation) {
        return db.deleteReservation(reservation.getReservationId(), reservation.getInvoiceId());
    }

    @Override
    public Response cancelReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
        return db.updateReservation(reservation.getReservationId(), reservation.getCustomerId(),
            reservation.getInvoiceId(), reservation.getRoomNumber(), reservation.getCreatedAt(),
            reservation.getArrival(), reservation.getDeparture(), reservation.getStatus());
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

        response = db.insertReservation(reservation.getReservationId(), reservation.getCustomerId(),
            reservation.getInvoiceId(), reservation.getRoomNumber(), reservation.getCreatedAt(),
            reservation.getArrival(), reservation.getDeparture(), reservation.getStatus());
        if (Response.FAILURE == response) {
            return null;
        }

        return reservation;
    }
}