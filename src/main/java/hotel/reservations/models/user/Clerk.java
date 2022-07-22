package hotel.reservations.models.user;

import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;

import java.util.UUID;

public class Clerk implements User {

    public final UUID userId;
    public final Account accountType;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active = true;
    private Guest customer = null;
    Database db;

    public Clerk(UUID userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.accountType = Account.CLERK;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * Attempts to lookup a guest in the db based on username
     * @param username
     * @return Guest instance on success, null on fail
     */
    public Guest getUser(String username) {
        Object obj = db.getUser(username);
        if (obj instanceof Guest) {
            return (Guest)obj;
        } else {
            return null;
        }
    }

    public Response setCustomer(String username) {
        Guest guest = getUser(username);
        if (guest != null) {
            setCustomer(guest);
            return Response.SUCCESS;
        }
        return Response.FAILURE;
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
        return this.lastName = lastName;
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

    public Response setCustomer(Guest guest) {
        if (guest != null) {
            this.setCustomer(guest);
            return Response.SUCCESS;
        }
        return Response.FAILURE;
    }

    @Override
    public Guest getCustomer() {
        return this.customer;
    }

    public Response updateUser(){
        return db.updateUserProfile(this);
    }

    public Response updateRoom (Room room) {

        return db.updateRoom(room);
    }
}