package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.IUser;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ApplicationController {
    // Use Case 01
    public void createReservation(IUser guest, Room room, LocalDate arrival, LocalDate departure);

    // Use Case 02
    public void cancelReservation(Reservation reservation);

    // Use Case 03
    public void modifyReservation(Reservation modifiedReservation);

    // Use Case 04
    public void checkIn(Reservation reservation);

    // Use Case 05
    public void checkOut(Reservation reservation);

    // Use Case 06
    public void viewReport(UUID sessionId);

    // Use Case 07
    public void addRoom(Room newRoom);

    // Use Case 08
    public void deleteRoom(Room room);

    // Use Case 09
    public void modifyRoom(Room modifiedRoom);

    // Use Case 10
    public void logIn(String username, char[] password);

    // Use Case 11
    public void registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String city, String state, String zipCode) throws NoSuchAlgorithmException, InvalidKeySpecException;

    // Use Case 12
    public void getInvoice(Reservation reservation);

    // Use Case 13
    public void viewStatus(List<Room> roomReport);

    // Use Case 14
    public void resetPassword(IUser user, char[] oldPassword, char[] newPassword);

    // Use Case 15
    public void modifyUser(IUser modifiedUser);

    // Use Case 16
    public void createClerk(IUser newClerk);

    // Use Case 17
    public void payInvoice(Reservation reservation);
}
