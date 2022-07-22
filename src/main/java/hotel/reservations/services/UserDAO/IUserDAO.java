package hotel.reservations.services.UserDAO;

import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.Guest;
import hotel.reservations.services.Response;

import java.util.ArrayList;
import java.util.UUID;

public interface IUserDAO {
    public Response changePassword(String username, String currentPassword, String newPassword);

    public Response setCustomer(Guest guest);

    public Response updateUser();

    public Response createUser(Account accountType, String username, String firstName, String lastName);

    public boolean authenticateUser(String username, String password);

    public Object getUserById(UUID userId);

    public Object getUserByUsername(String username);

    public void createUser(Account type, String username, String hashed_password,
                           String fName, String lName, boolean active);

    public ArrayList<Object> getAllUsers();


}
