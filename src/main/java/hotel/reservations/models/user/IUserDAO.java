package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.services.Response;

import java.time.LocalDate;
import java.util.UUID;

public interface IUserDAO {

    public UUID getUserId();

    public Account getAccountType();

    public void setUsername(String username);

    public String getUsername();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public boolean getActive();

    public void setActive(boolean active);

    public Response changePassword(String username, String currentPassword, String newPassword);

    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room);

    public Reservation createReservation(LocalDate arrival, LocalDate departure, int roomId);

    public Response setCustomer(Guest guest);
}
