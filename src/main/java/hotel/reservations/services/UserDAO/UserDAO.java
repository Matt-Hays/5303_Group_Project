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

    private Database db = null;
    User customer = null;
    private UserDAO(Database db) {
        this.db = Database.Database();
    }

    /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     */
    @Override
    public Response changePassword(String username, String currentPassword, String newPassword) {
        try {
            return db.updatePassword(username, currentPassword, newPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }


    public Response updateUser(User user) {
        return db.updateUserProfile(user);
    }

    public boolean authenticateUser(String username, String password){

        return false;
    }

    public Object getUserById(UUID userId) {

        return db.getUser(userId);
    }

    public Object getUserByUsername(String username){

        return db.getUser(username);
    }


    public Response updateUser(){
        return db.updateUserProfile(this);
    }

    public Response createDefaultUser(Account accountType, String username, String firstName, String lastName) {
        String hashed_password;

        try {
            hashed_password = HotelAuth.generatePasswordHash("password123$");
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            return Response.FAILURE;
        }

        if (accountType == Account.CLERK) {
            Clerk clerk = new Clerk(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(clerk, hashed_password);
        } else if (accountType == Account.ADMIN) {
            Admin admin = new Admin(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(admin, hashed_password);
        } else if (accountType == Account.GUEST) {
            Guest guest = new Guest(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(guest, hashed_password);
        }

        return Response.FAILURE;
    }

    public void createUser(Account type, String username, String hashed_password,
                           String fName, String lName, boolean active){
        db.insertUser(type, username, hashed_password, fName, lName, active);
    }

    public ArrayList<User> getAllUsers() {
        return db.getAllUsers();
    }

}
