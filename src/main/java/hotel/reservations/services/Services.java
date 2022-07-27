package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Clerk;
import hotel.reservations.models.user.Guest;
import hotel.reservations.models.user.User;
import hotel.reservations.views.controller.GuiHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface Services {
    // Use Case 01
    public Reservation createReservation(Reservation newReservation);

    // Use Case 02
    public void cancelReservation(Reservation reservation);

    // Use Case 03
    public Reservation modifyReservation(Reservation modifiedReservation);

    // Use Case 04
    public Reservation checkIn(Reservation reservation, Guest guest);

    // Use Case 05
    public Reservation checkOut(Reservation reservation, Guest guest);

    // Use Case 06
    public List<Room> viewReport(Session userSession);

    // Use Case 07
    public Room addRoom(Room newRoom);

    // Use Case 08
    public void deleteRoom(Room room);

    // Use Case 09
    public Room modifyRoom(Room modifiedRoom);

    // Use Case 10
    public Session logIn(User user);

    // Use Case 11
    public Session registerUser(User newUser);

    // Use Case 12
    public Invoice getInvoice(Invoice invoice);

    // Use Case 13
    public List<Room> viewStatus(Session session);

    // Use Case 14
    public void resetPassword(User user, char[] newPassword);

    // Use Case 15
    public User modifyUser(Session session, User modifiedUser);

    // Use Case 16
    public Clerk createClerk(Session session, Clerk newClerk);

    // Use Case 17
    public Invoice payInvoice(Invoice invoice, Payment payment);
}
