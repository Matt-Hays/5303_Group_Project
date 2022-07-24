package hotel.reservations.models.user;

import java.util.UUID;

public class Guest implements User {

    public final UUID userId;
    public final Account accountType = Account.GUEST;
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

    public Guest(UUID userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        setCustomer(this);
    }


    /**
     * Use standard setters and then call update to update db.
     * @return Response.SUCCESS, Response.FAILURE
     */

    @Override
    public String getUsername() {
        return this.username;
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


    public String getAddress1() {
        return this.address1;
    }


    public void setAddress1(String address1) {
        this.address1 = address1;
    }


    public String getAddress2() {
        return this.address2;
    }


    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    public String getCity() {
        return this.city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getState() {
        return this.state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getZip() {
        return this.zip;
    }


    public void setZip(String zip) {
        this.zip = zip;
    }


    public boolean isActive() {
        return active;
    }


    @Override
    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public void setCustomer(Guest guest) {
            this.customer = guest;
    }


//    public void setCustomer(String username) {
//        Object guest = db.getUser(username);
//    }


    @Override
    public Guest getCustomer() {

        return this.customer;
    }



}