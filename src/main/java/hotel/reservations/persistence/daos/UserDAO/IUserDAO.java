package hotel.reservations.persistence.daos.UserDAO;

import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.services.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.UUID;

public interface IUserDAO {
    public Response changePassword(String username, char[] currentPassword, char[] newPassword);

    public Response updateUser(UUID userId, String newUsername, String firstName, String lastName, String street,
                               String state, String zipCode, boolean active);

    public Response createDefaultUser(Account accountType, String username, String firstName, String lastName,
                                      String street, String state, String zipCode);

    public User logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public User getUserById(UUID userId);

    public User getUserByUsername(String username);

    public User createUser(Account accountType, String username, char[] password, String fName, String lName, String street,
                           String state, String zipCode) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public ArrayList<User> getAllUsers();


}