package hotel.reservations.models.user;


import java.util.UUID;

public class Admin implements User {

    public final UUID userId;
    public final Account accountType = Account.ADMIN;
    private String username;
    private String firstName;
    private String lastName, street, state, zipCode;
    private boolean active = true;
    private Guest customer = null;

    public Admin(UUID userId, String username, String firstName, String lastName, String street, String state,
                 String zipCode) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street=street;
        this.state=state;
        this.zipCode=zipCode;
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

    @Override
    public void setCustomer(Guest guest) {

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

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}