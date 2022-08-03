package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.Account;
import hotel.reservations.services.Response;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface IDatabase {

    // User methods
    public boolean ready();
    public ResultSet getUser(String username);
    public ResultSet getUser(UUID userId);
    public ResultSet getAllUsers();
    public String getPassword(String username);
    public Response insertUser(UUID userId, Account type, String username, String hashed_password,
                               String fName, String lName, String street, String state, String zipCode, Boolean active);
    public Response updateUserProfile(UUID userId, String newUsername, String firstName, String lastName,
									  String street, String state, String zipCode, boolean active);

    public Response updatePassword(String username, String newPasswordHash);

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
