package hotel.reservations.services;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.services.reservationDAO.IReservationDAO;

import java.util.Set;
import java.util.UUID;

/**
 * Provides service into the Reservation.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public class ReservationService implements Service<Reservation, UUID>, CheckInService {
    private final IReservationDAO reservationDAO;

    public ReservationService(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    public Reservation findById(UUID id) {
        return null;
    }

    @Override
    public Set<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation save(Reservation object) {
        return null;
    }

    @Override
    public Reservation create(Reservation object) {
        return null;
    }

    @Override
    public void delete(Reservation object) {

    }

    @Override
    public Reservation checkIn(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation checkOut(Reservation reservation) {
        return null;
    }
}
