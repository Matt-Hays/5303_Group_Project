/**
 * @author Christian Haddad
 */

package hotel.reservations.models.user;

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
    public String getStreet();
    public void setStreet(String street);
    public void setState(String state);
    public String getState();
    public void setZipCode(String zipCode);
    public String getZipCode();
    public boolean getActive();
    public void setActive(boolean active);
    void setCustomer(Guest guest);

    public Guest getCustomer();

}




