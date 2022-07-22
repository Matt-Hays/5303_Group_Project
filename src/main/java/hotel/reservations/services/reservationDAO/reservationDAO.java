package hotel.reservations.services.reservationDAO;

// import hotel.reservations.persistence.Database;
import hotel.reservations.models.reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface reservationDAO {
    /**
     * Returns a list of reservations that overlap with the requested arrival and departure dates
     * @param arrival arrival date
     * @param departure departure date
     * @return list of reservations
     */
    public ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure);

    public ArrayList<Reservation> findReservations(UUID guestId);

    public Reservation findReservation(UUID reservationId);

    public void updateReservation(Reservation reservation);
}
