package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.UUID;

public interface User {
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
    public Response setCustomer(Guest guest);
    public Guest getCustomer();
    public Response updateUser();
    public Response updateRoom (Room room);
}




