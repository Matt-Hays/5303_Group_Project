package hotel.reservations.controller;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;
import hotel.reservations.views.frame.Frame;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppController {
    /**                       *
     * User Service Endpoints *
     *                        */
    // Use Case 10
    public Session logIn(String username, char[] password);
    public void logOut(UUID sessionId);

    // Use Case 11
    public Session registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String state, String zipCode);

    // Use Case 14
    public void resetPassword(String username, char[] oldPassword, char[] newPassword);
    public void resetGuestPassword(UUID sessionId, String username);

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

    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking);

    public List<Room> getRooms();

    /**                              *
     * End of Room Service Endpoints *
     * ----------------------------- *
     * Billing Service Endpoints *
     *                               */

    public List<Invoice> generateBillingReport(String username);

    public List<Reservation> getReservationByUserId(UUID id);

    Reservation getReservationByReservationId(UUID id);

    /**
     * Not yet organized...
     *
     * @return
     */
    // Use Case 01
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure);
    public User getUser(String username);

    // Use Case 02
    public Response cancelReservation(Reservation reservation);

    // Use Case 03
    public Response modifyReservation(Reservation modifiedReservation);

    // Use Case 04
    public Response checkIn(Reservation reservation);

    // Use Case 05
    public Response checkOut(Reservation reservation);

    // Use Case 06
    public void viewReport(UUID sessionId);

    // Use Case 07
    public void createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);

    public Room getRoom(int roomId);

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
    public void addViewsHandler(Frame guiHandler);
}
