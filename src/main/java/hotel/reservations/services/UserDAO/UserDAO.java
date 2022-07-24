package hotel.reservations.services.UserDAO;

import hotel.reservations.models.user.*;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import hotel.reservations.services.authentication.HotelAuth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.UUID;

public class UserDAO implements IUserDAO <User> {

    private Database db;

    User customer;

    public UserDAO(Database db) {
        this.db = db;
    }

    /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     */
    @Override
    public Response changePassword(String username, char[] currentPassword, char[] newPassword) {
        try {
            if(HotelAuth.validatePassword(String.valueOf(currentPassword), db.getPassword(username))){
                return db.updatePassword(username, HotelAuth.generatePasswordHash(String.valueOf(newPassword)));
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }


    public Response updateUser(UUID userId, String newUsername, String firstName, String lastName, String address,
                               String state, String zipCode, boolean active) {
        return db.updateUserProfile(userId, newUsername, firstName, lastName, address, state, zipCode, active);
    }

    public User logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPw = db.getPassword(username);
        if(HotelAuth.validatePassword(String.valueOf(password), hashedPw)){
            User user = db.getUser(username);
            return user;
        }
        return null;
    }

    public Object getUserById(UUID userId) {

        return db.getUser(userId);
    }

    public Object getUserByUsername(String username){

        return db.getUser(username);
    }

    public Response createDefaultUser(Account accountType, String username, String firstName, String lastName,
                                      String address, String state, String zipCode) {
        String hashed_password;

        try {
            hashed_password = HotelAuth.generatePasswordHash("password123$");
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            return Response.FAILURE;
        }

        if (accountType == Account.CLERK) {
            Clerk clerk = new Clerk(UUID.randomUUID(), username.toLowerCase(), firstName, lastName, address, state,
                    zipCode);
            return db.insertUser(clerk, hashed_password);
        } else if (accountType == Account.ADMIN) {
            Admin admin = new Admin(UUID.randomUUID(), username.toLowerCase(), firstName, lastName, address, state,
                    zipCode);
            return db.insertUser(admin, hashed_password);
        } else if (accountType == Account.GUEST) {
            Guest guest = new Guest(UUID.randomUUID(), username.toLowerCase(), firstName, lastName, address, state,
                    zipCode);
            return db.insertUser(guest, hashed_password);
        }

        return Response.FAILURE;
    }

    @Override
    public User createUser(String username, char[] password, String fName, String lName, String address,
                           String state, String zipCode) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return db.insertUser(username, HotelAuth.generatePasswordHash(String.valueOf(password)), fName, lName, address,
                state,
                zipCode);
    }

    public ArrayList<User> getAllUsers() {
        return db.getAllUsers();
    }

}
