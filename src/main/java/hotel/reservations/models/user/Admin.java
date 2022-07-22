package hotel.reservations.models.user;

import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.authentication.HotelAuth;
import hotel.reservations.services.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Admin implements User {

    public final UUID userId;
    public final Account accountType = Account.ADMIN;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active = true;
    private Guest customer = null;
    Database db;

    public Admin(UUID userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        db = Database.Database();
    }


    @Override
    public UUID getUserId() {
        return this.userId;
    }

    @Override
    public Account getAccountType() {
        return this.accountType;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
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