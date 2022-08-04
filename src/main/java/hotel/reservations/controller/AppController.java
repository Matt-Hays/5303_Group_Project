package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.views.controller.GuiHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppController {
    /**                       *
     * User Service Endpoints *
     *                        */
    // Use Case 10
    public UUID logIn(String username, char[] password);
    public void logOut(UUID sessionId);

    // Use Case 11
    public UUID registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String state, String zipCode);

    // Use Case 14
    public void resetPassword(String username, char[] oldPassword, char[] newPassword);

    // Use Case 15
    public User modifyUser(UUID sessionId, String newUsername, String firstName, String lastName, String address, String state,
                           String zipCode, boolean active);

    // Use Case 16
    public void createClerk(String username, String firstName, String lastName, String address, String state,
                            String zipCode);

    /**                              *
     * End of User Service Endpoints *
     * ----------------------------- *
     *    Room Service Endpoints     *
     *                               */

    /**
     * Not yet organized...
     */
    // Use Case 01
    public void createReservation(User guest, Room room, LocalDate arrival, LocalDate departure);

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



    // Use Case 12
    public void getInvoice(Reservation reservation);

    // Use Case 13
    public void viewStatus(List<Room> roomReport);



    // Use Case 17
    public void payInvoice(Reservation reservation);

    // Misc. Methods
    public void addViewsHandler(GuiHandler guiHandler);
}
