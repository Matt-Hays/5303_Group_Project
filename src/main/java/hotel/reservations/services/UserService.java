package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;

import java.util.UUID;

public interface UserService  {
    // Log in
    public UUID login(String username, char[] password);
    // Log out
    public Session logout(Session session);
    // Create a User
    public UUID createUser(Account accountType, String username, char[] password, String fName, String lName, String street, String state, String zipCode);
    // Modify a User
    public User updateUser(User updatedUser);
    // Delete a User
    public void deleteUser(User user);
    public void deleteUserById(UUID id);
}
