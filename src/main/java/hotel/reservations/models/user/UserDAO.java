package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import hotel.reservations.services.UserCatalog;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.UUID;

public class UserDAO implements IUserDAO{

    private Database db = null;
    private UserDAO() { db = Database.Database(); }


    @Override
    public UUID getUserId() {
        return null;
    }

    @Override
    public Account getAccountType() {
        return null;
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public boolean getActive() {
        return false;
    }

    @Override
    public void setActive(boolean active) {

    }

    @Override
    public Response changePassword(String username, String currentPassword, String newPassword) {
        try {
            return db.updatePassword(username, currentPassword, newPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }

    @Override
    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room) {
        return null;
    }

    @Override
    public Reservation createReservation(LocalDate arrival, LocalDate departure, int roomId) {
        return null;
    }

    @Override
    public Response setCustomer(Guest guest) {
        return null;
    }
}
