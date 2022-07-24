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
    private String street, state, zipCode;
    private boolean active = true;
    private Guest customer = null;

    public Clerk(UUID userId, String username, String firstName, String lastName, String street, String state,
                 String zipCode) {
        this.userId = userId;
        this.accountType = Account.CLERK;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street=street;
        this.state=state;
        this.zipCode=zipCode;
    }

    public String getAddress() {
        return street;
    }

    public void setAddress(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isActive() {
        return active;
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

    public void setCustomer(Guest guest) {
            this.setCustomer(guest);
    }

    @Override
    public Guest getCustomer() {
        return this.customer;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }


}