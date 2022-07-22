package hotel.reservations.services.reservationDAO;

import hotel.reservations.persistence.Database;

public class ReservationCatalog {
    private static ReservationCatalog reservationCatalog = null;
    private Database db = null;

    private ReservationCatalog() {
        db = Database.Database();
    }

}
