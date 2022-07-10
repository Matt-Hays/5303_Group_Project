package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import java.time.LocalDate;
import java.util.ArrayList;

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
    public static ReservationCatalog getReservationCatalog() {
        if (null == reservationCatalog) {
            reservationCatalog = new ReservationCatalog();
        }

        return reservationCatalog;
    }

    /**
     * Returns a list of reservations that overlap with the requested arrival and depaqrture dates
     * @param arrival arrival date
     * @param departure departure date
     * @return list of reservations
     */
    ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure) {
        return db.getOverlappingReservations(arrival, departure);
    }
}
