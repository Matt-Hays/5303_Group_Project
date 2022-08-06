package hotel.reservations.services.impls;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.ReservationDao;
import hotel.reservations.persistence.dao.impls.ReservationDaoImpl;
import hotel.reservations.services.ReservationService;

import java.time.LocalDate;
import java.util.UUID;

public class ReservationServiceImpl implements ReservationService {
    ReservationDaoImpl reservationDao = null;

    public ReservationServiceImpl(ReservationDaoImpl reservationDao) {
        this.reservationDao = reservationDao;
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
}
