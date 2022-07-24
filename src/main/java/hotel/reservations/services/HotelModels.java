package hotel.reservations.services;

import hotel.reservations.models.user.Account;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class HotelModels {
    private static ReservationCatalog reservationCatalog;
    private static UserCatalog userCatalog;
    private static SessionCatalog sessionCatalog;

    public HotelModels() {
        this.reservationCatalog = ReservationCatalog.getReservationCatalog();
        this.userCatalog = UserCatalog.getUserCatalog();
        this.sessionCatalog = SessionCatalog.getSessionCatalog();
    }

    public static ArrayList<Room> searchAvailableRooms(LocalDate arrival, LocalDate departure) {
        return roomCatalog.filterRooms(arrival, departure);
    }

    public static Object getUserByUsernameCatalog(String username){
        return userCatalog.getUserByUsername(username);
    }

    public static UUID createNewSession(User user){
        return sessionCatalog.createSession(user);
    }

    public static Object getSessionUser(String sessionId){
        return sessionCatalog.getSession(sessionId);
    }


    public static void createGuest(Account type, String username, String hashed_password,
                                   String fName, String lName, boolean active){
        userCatalog.createNewGuest(type, username, hashed_password, fName, lName, active);
    }

    public static Room getRoom(String roomId){
        return roomCatalog.getRoom(roomId);
    }

    public static ArrayList<Room> getAllRooms(){
        return roomCatalog.getAllRooms();
    }

    public static Response modifyRoom(Room room){
        return roomCatalog.updateRoom(room);
    }

    public static void createClerk(String username, String hashed_password, String fName, String lName){
        userCatalog.createNewGuest(Account.CLERK, username, hashed_password, fName, lName, true);
    }

    /* Commenting out and moving these to the Reservation DAO Interface
    public ArrayList<Reservation> getAllGuestReservations(UUID userId){
        return reservationCatalog.findReservations(userId);
    }

    public Reservation getReservation(UUID reservationId){
        return reservationCatalog.findReservation(reservationId);
    }

    public void updateReservation(Reservation reservation){
        reservationCatalog.updateReservation(reservation);
    }

    public void logout(UUID sessionId){
        sessionCatalog.deleteSession(sessionId);
    }
     */
}
