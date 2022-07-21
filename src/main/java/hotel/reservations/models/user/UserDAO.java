package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import hotel.reservations.services.UserCatalog;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.UUID;

public class UserDAO implements IUserDAO {

    private Database db = null;
    private UserDAO() { db = Database.Database(); }

    /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     */
    @Override
    public Response changePassword(String username, String currentPassword, String newPassword) {
        try {
            return db.updatePassword(username, currentPassword, newPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }

    @Override
    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room, User user) {

        if (null != user.getCustomer()) {
            return new Reservation(user.getCustomer(), room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);
        }
        return null;
    }

    @Override
    public Reservation createReservation(LocalDate arrival, LocalDate departure, int roomId, User user) {
        if (null != user.getCustomer()) {
            Room room = db.getRoom(roomId);
            if (null != room) {
                return new Reservation(user.getCustomer(), room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);
            }
        }
        return null;
    }

    @Override
    public Response setCustomer(Guest guest) {
        if (guest != null) {
            guest.setCustomer(guest);
            return Response.SUCCESS;
        }
        return Response.FAILURE;
    }


    public Response updateUser(){
        return db.updateUserProfile(this);
    }

}
