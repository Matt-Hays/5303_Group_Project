package hotel.reservations.services;

import hotel.reservations.persistence.Database;
import hotel.reservations.models.user.Account;


import java.util.ArrayList;
import java.util.UUID;

public class UserCatalog {
    public static UserCatalog userCatalog = null;

    private Database db = null;

    private UserCatalog() {
        db = Database.Database();
    }

    public static UserCatalog getUserCatalog() {
        if(null == userCatalog) {
            userCatalog = new UserCatalog();
        }

        return userCatalog;
    }

    public Object getUserByUsername(String username) {
        return db.getUser(username);
    }

    public Object getUserById(UUID userId) {
        return db.getUser(userId);
    }

    public ArrayList<Object> getAllUsers() {
        return db.getAllUsers();
    }

    public void createNewGuest(Account type, String username, String hashed_password,
                               String fName, String lName, boolean active){
        db.insertUser(type, username, hashed_password, fName, lName, active);
    }

}