package hotel.reservations.persistence.daos.UserDAO;

import hotel.reservations.models.user.*;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import hotel.reservations.services.authentication.HotelAuth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.sql.ResultSet;

public class UserDAO implements IUserDAO {

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

    /**
     * Update user profile in db
     * @param userId
     * @param newUsername
     * @param firstName
     * @param lastName
     * @param street
     * @param state
     * @param zipCode
     * @param active
     * @return
     */
    public Response updateUser(UUID userId, String newUsername, String firstName, String lastName, String street,
                               String state, String zipCode, boolean active) {
        return db.updateUserProfile(userId, newUsername, firstName, lastName, street, state, zipCode, active);
    }

    /**
     * Validats a user account for login
     * @param username
     * @param password
     * @return User object or null on failure
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public User logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPw = db.getPassword(username);
        if(HotelAuth.validatePassword(String.valueOf(password), hashedPw)){
            ResultSet rs = db.getUser(username);
            return createUser(rs);
        }
        return null;
    }

    /**
     * Lookup a user based on user id
     * @param userId
     * @return User object or null on failure
     */
    public User getUserById(UUID userId) {
        ResultSet rs = db.getUser(userId);
        return createUser(rs);
    }

    /**
     * Lookup a user based on their username
     * @param username
     * @return User object or null on failure
     */
    public User getUserByUsername(String username){

        ResultSet rs = db.getUser(username);
        return createUser(rs);
    }

    /**
     * helper method for create a user from a ResultSet
     * @param rs
     * @return User object or null on failure
     */
    private User createUser(ResultSet rs) {
        if (null == rs) {
            return null;
        }

        try {
            UUID userId = UUID.fromString(rs.getString("id"));
            String username = rs.getString("username");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String street = rs.getString("street");
            String state = rs.getString("state");
            String zip = rs.getString("zip");
            boolean active = rs.getBoolean("active");
            Account accountType = Account.valueOf(rs.getString("type"));

            switch (accountType) {
                case CLERK:
                    return new Clerk(userId, username.toLowerCase(), firstName, lastName, street, state, zip, active);
                case ADMIN:
                    return new Admin(userId, username.toLowerCase(), firstName, lastName, street, state, zip, active);
                case GUEST:
                    return new Guest(userId, username.toLowerCase(), firstName, lastName, street, state, zip, active);
            }

        } catch (SQLException e) {
        }

        return null;
    }



    /**
     * Create a user with default password
     */
    public Response createDefaultUser(Account accountType, String username, String firstName, String lastName,
                                      String street, String state, String zipCode) {
        String hashed_password;

        try {
            hashed_password = HotelAuth.generatePasswordHash("password123$");
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            return Response.FAILURE;
        }

        return db.insertUser(UUID.randomUUID(), accountType, username, hashed_password, firstName, lastName, street, state, zipCode, true);
    }

    /**
     * create a brand new user and save in the db
     * @param accountType
     * @param username
     * @param password
     * @param fName
     * @param lName
     * @param street
     * @param state
     * @param zipCode
     * @return User object or null on failure
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Override
    public User createUser(Account accountType, String username, char[] password, String fName, String lName, String street,
                           String state, String zipCode) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Response response =  db.insertUser(UUID.randomUUID(), accountType, username, HotelAuth.generatePasswordHash(String.valueOf(password)),
            fName, lName, street, state, zipCode, true);

        if (response == Response.FAILURE) {
            return null;
        }

        return getUserByUsername(username);
    }

    /**
     * Return an array of all users
     * @return array of user objects or an empty array on failure
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<User>();

        ResultSet rs = db.getAllUsers();
        if (null == rs) {
            return allUsers;
        }

        try {
            if (rs.next()) {
                allUsers.add(createUser(rs));
            }
        } catch (SQLException e) {
            return allUsers;
        }

        return allUsers;

    }

}
