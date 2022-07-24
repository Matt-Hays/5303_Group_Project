package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.services.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface IDatabase {

    // User methods
    public boolean ready();
    public User getUser(String username);
    public ArrayList<User> getAllUsers();
    public User getUser(UUID userId);
    public String getPassword(String username);
    public Response insertUser(User user, String hashed_password);
    public User insertUser(String username, String hashed_password,
                               String fName, String lName, String address, String city, String state, String zipCode);
    public Response updateUserProfile(UUID userId, String newUsername, String firstName, String lastName,
                                      String address, String state, String zipCode, boolean active);
    public Response updatePassword(String username, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;

    // room methods
    public Room getRoom(int roomId);
    public ArrayList<Room> getAllRooms();
    public Response updateRoom (Room room);

    // reservation methods
    public Reservation getReservation(UUID reservationId);
    public ArrayList<Reservation> getReservationByGuestId(UUID customerId);
    public Response insertReservation(Reservation r);
    public Response updateReservation(Reservation r);
    public Response deleteReservation(Reservation r);
    public ArrayList<Reservation> getOverlappingReservations(LocalDate arrival, LocalDate departure);

    // invoice methods
    public Response insertInvoice(Invoice i);
    public Invoice getInvoice(UUID invoiceId);
    public Response updateInvoice(Invoice i);
}
