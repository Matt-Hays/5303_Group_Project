package hotel.reservations.models.user;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.UUID;

abstract class User {
    public final UUID userId;
    public final Account accountType;
    private String username;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private boolean active = true;
    private Guest customer = null;
    Database db;

    public User(UUID userId, Account accountType, String username, String firstName,
                String lastName) {
        this.userId = userId;
        this.accountType = accountType;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUserId(){
        return this.userId;
    }

    public Account getAccountType() {
        return this.accountType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     */
    public Response changePassword(String username, String currentPassword, String newPassword) {

        try {
            return db.updatePassword(username, currentPassword, newPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }

    /**
     * Creates a reservation when you already have a room object.
     * @param arrival
     * @param departure
     * @param room
     * @return Reservation instance on success, null on failure
     */
    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room) {
        if (null != customer) {
            return new Reservation(customer, room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);
        }
        return null;
    }

    /**
     * Used to create a reservation when you only have the roomId
     * @param arrival
     * @param departure
     * @param roomId
     * @return Reservation on success, null on failure
     */
    public Reservation createReservation(LocalDate arrival, LocalDate departure, int roomId) {
        if (null != customer) {
            Room room = db.getRoom(roomId);
            if (null != room) {
                return new Reservation(customer, room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);
            }
        }
        return null;
    }

    public Response setCustomer(Guest guest) {
        if (guest != null) {
            customer = guest;
            return Response.SUCCESS;
        }
        return Response.FAILURE;
    }


    public Guest getCustomer() {
        return customer;
    }

    public Response updateUser(){
        return db.updateUserProfile(this);
    }
}