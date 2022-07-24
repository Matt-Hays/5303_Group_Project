package hotel.reservations.services.UserDAO;

import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.Guest;
import hotel.reservations.models.user.User;
import hotel.reservations.services.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.UUID;

public interface IUserDAO<T> {
    public Response changePassword(String username, String currentPassword, String newPassword);

    public Response updateUser(T user);

    public Response createDefaultUser(Account accountType, String username, String firstName, String lastName);

    public User logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public Object getUserById(UUID userId);

    public Object getUserByUsername(String username);

    public void createUser(Account type, String username, String hashed_password,
                           String fName, String lName, boolean active);

    public ArrayList<User> getAllUsers();


}
