package hotel.reservations.services.reservationDAOImpl;

import hotel.reservations.services.reservationDAO;
import hotel.reservations.persistence.Database;
import hotel.reservations.models.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class reservationDAOImpl implements reservationDAO {
    private static reservationDAOImpl dao = null;
    private static Database db = null;

    private reservationDAOImpl() {
    }
    public static reservationDAOImpl getReservationDAO() {
        if (null == dao) {
            dao = new reservationDAOImpl();
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

}
