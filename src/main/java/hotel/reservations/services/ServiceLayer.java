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

    @Override
    public Reservation createReservation(Reservation newReservation) {
        return reservationDAO.createReservation(newReservation);
    }

    @Override
    public void cancelReservation(Reservation reservation) {
        reservationDAO.cancelReservation(reservation);
    }

    @Override
    public Reservation modifyReservation(Reservation modifiedReservation) {
        return reservationDAO.updateReservation(modifiedReservation);
    }

    @Override
    public Reservation checkIn(Reservation reservation, Guest guest) {
        return reservationDAO.checkIn(reservation, guest);
    }

    @Override
    public Reservation checkOut(Reservation reservation, Guest guest) {
        return reservationDAO.checkOut(reservation, guest);
    }

    @Override
    public List<Room> viewReport(Session userSession) {
        if(userSession.getUser().getAccountType().equals(Account.ADMIN)){

        }
        return null;
    }

    @Override
    public Room addRoom(Room newRoom) {
        return roomDAO.addRoom(newRoom);
    }

    @Override
    public void deleteRoom(Room room) {
        roomDAO.deleteRoom(room);
    }

    @Override
    public Room modifyRoom(Room modifiedRoom) {
        return roomDAO.updateRoom(modifiedRoom);
    }

    @Override
    public Session logIn(User user) {
        User authenticatedUser = userDAO.logIn(user);
        if(user != null)
            return sessionDAO.createSession(authenticatedUser);
        return null;
    }

    @Override
    public Session registerUser(User newUser) {
        User user = userDAO.createUser(newUser);
        if(user != null)
            return sessionDAO.createSession(newUser);
        return null;
    }

    @Override
    public Invoice getInvoice(Invoice invoice) {
        return null;
    }

    @Override
    public List<Room> viewStatus(Session session) {
        return null;
    }

    @Override
    public void resetPassword(User user, char[] newPassword) {
        User validatedUser = userDAO.logIn(user);
        if(validatedUser != null)
            userDAO.changePassword(validatedUser, newPassword);
    }

    @Override
    public User modifyUser(Session session, User modifiedUser) {
        if(userDAO.getUser(modifiedUser).getUserId().equals(session.getUser().getUserId()))
            return userDAO.updateUser(modifiedUser);
        return null;
    }

    @Override
    public Clerk createClerk(Session session, Clerk newClerk) {
        if(sessionDAO.validateSession(session).equals(Account.ADMIN))
            return userDAO.createUser(newClerk);
        return null;
    }

    @Override
    public Invoice payInvoice(Invoice invoice, Payment payment) {
        return null;
    }
}
