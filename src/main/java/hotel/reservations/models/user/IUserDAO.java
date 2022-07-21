package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.services.Response;

import java.time.LocalDate;
import java.util.UUID;

public interface IUserDAO {
    public Response changePassword(String username, String currentPassword, String newPassword);

    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room, User user);

    public Reservation createReservation(LocalDate arrival, LocalDate departure, int roomId, User user);

    public Response setCustomer(Guest guest);

    public Response updateUser();


}
