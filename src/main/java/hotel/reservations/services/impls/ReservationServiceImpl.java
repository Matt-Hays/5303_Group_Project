package hotel.reservations.services.impls;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.ReservationDao;
import hotel.reservations.persistence.dao.UserDao;
import hotel.reservations.services.ReservationService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final UserDao userDao;

    public ReservationServiceImpl(ReservationDao reservationDao, UserDao userDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
    }

    @Override
    public Response checkIn(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.AWAITING) {
            return Response.FAILURE;
        }
        reservation.setStatus(ReservationStatus.CHECKEDIN);
        reservation.setCheckIn(LocalDate.now());
        return reservationDao.updateReservation(reservation);
    }

    @Override
    public Response checkOut(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.CHECKEDIN) {
            return Response.FAILURE;
        }
        reservation.setStatus(ReservationStatus.COMPLETE);
        reservation.setCheckout(LocalDate.now());

        UUID invoiceId = reservation.getInvoiceId();
        Invoice invoice = reservationDao.getInvoice(reservation.getInvoiceId());
        if (null == invoice) {
            return Response.FAILURE;
        }

        // update subtotal in case dates changed
        invoice.setSubtotal(invoice.getNightly_rate(), reservation.lengthOfStay());
        Response response = reservationDao.updateInvoice(invoice);
        if (response == Response.FAILURE) {
            return Response.FAILURE;
        }

        return reservationDao.updateReservation(reservation);
    }

    @Override
    public List<Reservation> findReservationByUsername(String username) {
        UUID guestId = userDao.getUserByUsername(username).getUserId();
        return reservationDao.findReservations(guestId);
    }

    @Override
    public Reservation findReservationByReservationId(UUID id) {
        return reservationDao.findReservation(id);
    }

    @Override
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        return reservationDao.createReservation(guest, room, arrival, departure);
    }

    @Override
    public Reservation clerkCreateReservation(String username, Room room, LocalDate arrival, LocalDate departure) {
        User guest = userDao.getUserByUsername(username);
        return reservationDao.createReservation(guest, room, arrival, departure);
    }

    @Override
    public Response cancelReservation(Reservation reservation) {
        return reservationDao.cancelReservation(reservation);
    }

    @Override
    public Response modifyReservation(Reservation modifiedReservation) {
        return reservationDao.updateReservation(modifiedReservation);
    }
}
