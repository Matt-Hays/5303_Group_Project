/**
 * The concrete implementation of the Services interface.
 * Provides manipulation of data access objects to accomplish application requirements.
 *
 * @author Matthew Hays
 * @version 1.0
 *     - Initial draft 7/27/22 Matthew Hays
 */

package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.ISessionDAO;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.session.SessionDAO;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.Clerk;
import hotel.reservations.models.user.Guest;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.IDatabase;
import hotel.reservations.services.UserDAO.IUserDAO;
import hotel.reservations.services.UserDAO.UserDAO;
import hotel.reservations.services.reservationDAO.IReservationDAO;
import hotel.reservations.services.reservationDAO.ReservationDAO;
import hotel.reservations.services.roomDAO.IRoomDAO;
import hotel.reservations.services.roomDAO.RoomDAO;

import java.util.List;

public class ServiceLayer implements Services {
    private IUserDAO userDAO;
    private ISessionDAO sessionDAO;
    private IReservationDAO reservationDAO;
    private IRoomDAO roomDAO;

    public ServiceLayer(IDatabase database){
        this.userDAO = new UserDAO(database);
        this.sessionDAO = new SessionDAO();
        this.reservationDAO = new ReservationDAO(database);
        this.roomDAO = new RoomDAO(database);
    }

    /**
     * Use Case 01 - Create Reservation
     * @param newReservation The new Reservation object passed from the GUI.
     * @return A created Reservation from the persistence layer.
     */
    @Override
    public Reservation createReservation(Reservation newReservation) {
        return reservationDAO.createReservation(newReservation);
    }

    /**
     * Use Case 02 - Cancel Reservation
     * @param reservation The Reservation to be cancelled.
     */
    @Override
    public void cancelReservation(Reservation reservation) {
        reservationDAO.cancelReservation(reservation);
    }

    /**
     * Use Case 03 - Modify Reservation
     * @param modifiedReservation The modified Reservation object passed from the GUI.
     * @return A modified Reservation from the persistence layer.
     */
    @Override
    public Reservation modifyReservation(Reservation modifiedReservation) {
        return reservationDAO.updateReservation(modifiedReservation);
    }

    /**
     * Use Case 04 - Check In
     * @param reservation The Reservation to check in.
     * @param guest The guest associated with the check in.
     * @return The checked-in Reservation from the persistence layer.
     */
    @Override
    public Reservation checkIn(Reservation reservation, Guest guest) {
        return reservationDAO.checkIn(reservation, guest);
    }

    /**
     * Use Case 05 - Check Out
     * @param reservation The Reservation to check out.
     * @param guest The guest associated with the check out.
     * @return The checked-out Reservation.
     */
    @Override
    public Reservation checkOut(Reservation reservation, Guest guest) {
        return reservationDAO.checkOut(reservation, guest);
    }

    /**
     * Use Case 06 - View Report
     * @param userSession The user's Session.
     * @return A list of Rooms sorted.
     */
    @Override
    public List<Room> viewReport(Session userSession) {
        if(userSession.getUser().getAccountType().equals(Account.ADMIN)){

        }
        return null;
    }

    /**
     * Use Case 07 - Add Room
     * @param newRoom The new Room to add from the GUI.
     * @return The new Room from the persistence layer.
     */
    @Override
    public Room addRoom(Room newRoom) {
        return roomDAO.addRoom(newRoom);
    }

    /**
     * Use Case 08 - Delete Room
     * @param room The Room to be deleted.
     */
    @Override
    public void deleteRoom(Room room) {
        roomDAO.deleteRoom(room);
    }

    /**
     * USe Case 09 - Modify Room
     * @param modifiedRoom The modified Room from the GUI.
     * @return The modified Room from the persistence layer.
     */
    @Override
    public Room modifyRoom(Room modifiedRoom) {
        return roomDAO.updateRoom(modifiedRoom);
    }

    /**
     * Use Case 10 - Log In
     * @param user The user attempting to log in.
     * @return A valid Session or null if not authenticated.
     */
    @Override
    public Session logIn(User user) {
        User authenticatedUser = userDAO.logIn(user);
        if(user != null)
            return sessionDAO.createSession(authenticatedUser);
        return null;
    }

    /**
     * USe Case 11 - Register User
     * @param newUser The new User from the GUI.
     * @return A valid Session or null if creation failure occurs.
     */
    @Override
    public Session registerUser(User newUser) {
        User user = userDAO.createUser(newUser);
        if(user != null)
            return sessionDAO.createSession(newUser);
        return null;
    }

    /**
     * Use Case 12 - Get Invoice
     * @param invoice The desired Invoice.
     * @return The Invoice from the persistence layer.
     */
    @Override
    public Invoice getInvoice(Invoice invoice) {
        return null;
    }

    /**
     * Use Case 13 - View Status
     * @param session The user's Session.
     * @return A list of Rooms.
     */
    @Override
    public List<Room> viewStatus(Session session) {
        return null;
    }

    /**
     * Use Case 14 - Reset Password
     * @param user The user requesting reset.
     * @param newPassword The desired new password.
     */
    @Override
    public void resetPassword(User user, char[] newPassword) {
        User validatedUser = userDAO.logIn(user);
        if(validatedUser != null)
            userDAO.changePassword(validatedUser, newPassword);
    }

    /**
     * Use Case 15 - Modify User
     * @param session The user's Session.
     * @param modifiedUser The modified User from the GUI.
     * @return The modified User from the persistence layer.
     */
    @Override
    public User modifyUser(Session session, User modifiedUser) {
        if(userDAO.getUser(modifiedUser).getUserId().equals(session.getUser().getUserId()))
            return userDAO.updateUser(modifiedUser);
        return null;
    }

    /**
     * Use Case 16 - Crete Clerk
     * @param session The user's Session.
     * @param newClerk The new Clerk from the GUI.
     * @return The new Clerk from the persistence layer.
     */
    @Override
    public Clerk createClerk(Session session, Clerk newClerk) {
        if(sessionDAO.validateSession(session).equals(Account.ADMIN))
            return userDAO.createUser(newClerk);
        return null;
    }

    /**
     * Use Case 17 - Pay Invoice
     * @param invoice The Invoice to be paid.
     * @param payment The Payment to be made.
     * @return The paid Invoice.
     */
    @Override
    public Invoice payInvoice(Invoice invoice, Payment payment) {
        return null;
    }
}
