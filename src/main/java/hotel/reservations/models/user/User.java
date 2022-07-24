package hotel.reservations.models.user;

import hotel.reservations.services.Response;

import java.util.UUID;

public interface User {
    public UUID getUserId();
    public Account getAccountType();
    public void setUsername(String username);
    public String getUsername();
    public String getFirstName();
    public void setFirstName(String firstName);
    public String getLastName();
    public void setLastName(String lastName);
    public boolean getActive();
    public void setActive(boolean active);
    void setCustomer(Guest guest);

    public Guest getCustomer();

}




