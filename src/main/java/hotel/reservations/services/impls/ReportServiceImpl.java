package hotel.reservations.services.impls;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.dao.ReservationDao;
import hotel.reservations.persistence.dao.UserDao;
import hotel.reservations.services.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    ReservationDao reservationDao = null;
    UserDao userDao = null;

    public ReportServiceImpl(ReservationDao reservationDao, UserDao userDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
    }

    public List<Invoice> getInvoiceList(String username){

        User guest = userDao.getUserByUsername(username);

        List<Reservation> allReservations = reservationDao.findReservations(guest.getUserId());
        List<Invoice> invoices = new ArrayList<Invoice>();

        for(int i = 0; i < allReservations.size(); i++) {

            if (!reservationDao.getInvoice(allReservations.get(i).getInvoiceId()).getIsPaid()) {
                invoices.add(reservationDao.getInvoice(allReservations.get(i).getInvoiceId()));
            }
        }

        return invoices;
    }


}
