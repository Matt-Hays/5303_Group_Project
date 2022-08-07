package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;

import java.util.UUID;

/**
 * The UserService interface.
 * Provides an interface into the User domain.
 */
public interface UserService  {
    // Log in
    public Session login(String username, char[] password);
    // Log out
    public Response logout(UUID id);
    // Create a User
    public Session createUser(Account accountType, String username, char[] password, String fName, String lName,
                           String street, String state, String zipCode);

    public Response createClerk(String username, String firstName, String lastName, String street, String state,
                            String zipCode);
    // Modify a User
    public User updateUser(UUID id, String newUsername, String firstName, String lastName, String street, String state,
                               String zipCode, boolean active);

    public Response updatePassword(String username, char[] oldPassword, char[] newPassword);
    public Response resetGuestPassword(UUID sessionId, String username);
    // Delete a User
    public Response deleteUser(User user);
    public Response deleteUserById(UUID id);

    public User getUserByUsername(String username);
}
