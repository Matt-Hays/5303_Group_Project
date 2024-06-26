/**
 * @file Database.java
 * @author Joshua Wellman
 * @brief Provides an interface to interact with the underlying
 *        physical database connection. Abstracts the PreparedStatements
 *        necessary to facilitate database communication.
 */

package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.*;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.user.Account;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.UUID;

public interface Database {

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
    public ResultSet getRoom(int roomId);
    public ResultSet getAllRooms();
    public Response updateRoom (int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response insertRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response deleteRoom(int roomId);

    // reservation methods
    public ResultSet getReservationByReservationId(UUID reservationId);
    public ResultSet getReservationByGuestId(UUID customerId);
    public Response insertReservation(UUID reservationId, UUID customerId, UUID invoiceId, int roomId,
			LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status);
    public Response updateReservation(UUID reservationId, UUID customerId, UUID invoiceId, int roomId,
    LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status);
    public Response deleteReservation(UUID reservationId, UUID invoiceId);
    public ResultSet getOverlappingReservations(LocalDate arrival, LocalDate departure);

    // invoice methods
    public Response insertInvoice(Invoice i);
    public Invoice getInvoice(UUID invoiceId);
    public Response updateInvoice(Invoice i);
    // gets deleted when a reservation is deleted
//    public Response deleteInvoice(UUID invoiceId);
}
