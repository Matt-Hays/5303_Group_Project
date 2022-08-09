/**
 * @file User.java
 * @author Christian Haddad
 * @brief A User interface that allows for generic passing of an object implementing
 *        User domain specific functions such as getAccountType(), getUsername(), etc.
 *        such that the application can make state decisions based upon the implementing type
 *        of the passed object.
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




