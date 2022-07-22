package hotel.reservations.services.reservationDAO;

import hotel.reservations.persistence.Database;
import hotel.reservations.models.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ReservationCatalog {
    private static ReservationCatalog reservationCatalog = null;
    private Database db = null;

    private ReservationCatalog() {
        db = Database.Database();
    }

    /**
     * method for creating a singleton
     * @return
     */
    public static ReservationDAO getReservationDAO() {
        if (null == reservationDAO) {
            reservationDAO = new ReservationDAO();
        }

        return reservationDAO;
    }

}
