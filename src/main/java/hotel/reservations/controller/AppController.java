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

    public Session logIn(String username, char[] password);

    public Response logOut(UUID sessionId);

    // Use Case 11
    public Session registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String state, String zipCode);

    // Use Case 14
    public Response resetPassword(String username, char[] oldPassword, char[] newPassword);
    public Response resetGuestPassword(UUID sessionId, String username);

    // Use Case 15
    public User modifyUser(UUID sessionId, String newUsername, String firstName, String lastName, String address, String state,
                           String zipCode, boolean active);

    // Use Case 16
    public Response createClerk(String username, String firstName, String lastName, String address, String state,
                            String zipCode);

    /**                              *
     * End of User Service Endpoints *
     * ----------------------------- *
     *    Room Service Endpoints     *
     *                               */

    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking);
    public List<Room> getRooms();
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Room getRoom(int roomId);
    public Response deleteRoom(int roomId);
    public Response updateRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);

    /**                              *
     * End of Room Service Endpoints *
     * ----------------------------- *
     *   Billing Service Endpoints   *
     *                               */

    public List<Invoice> generateBillingReport(String username);

    /**                                 *
     * End of Billing Service Endpoints *
     * -------------------------------- *
     *  Reservation Service Endpoints   *
     *                                  */

    public List<Reservation> getReservationByUsername(String username);
    Reservation getReservationByReservationId(UUID id);
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure);
    public Reservation clerkCreateReservation(String username, Room room, LocalDate arrival, LocalDate departure);
    public Response cancelReservation(Reservation reservation);
    public Response modifyReservation(Reservation modifiedReservation);
    public Response checkIn(Reservation reservation);
    public Response checkOut(Reservation reservation);

    /**                                     *
     * End of Reservation Service Endpoints *
     * ------------------------------------ *
     *   Unimplemented Service Endpoints    *
     *                                      */

    // Use Case 17
    public Response payInvoice(Reservation reservation);
}
