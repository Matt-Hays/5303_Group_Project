package com.gof.hr2s.user;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.utils.HotelAuth;
import com.gof.hr2s.utils.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.gof.hr2s.utils.HotelAuth.generatePasswordHash;

public class User {
    public final int userId;
    public final Account accountType;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active = true;
    public Database db = null;

    public User(int userId, Account accountType, String username) {
        this.userId = userId;
        this.accountType = accountType;
        this.username = username;
        db = Database.Database();
    }

    public User(int userId, Account accountType, String username, String firstName, String lastName) {
        this(userId, accountType, username);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account getAccountType() {
        return this.accountType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Need to be able to modify aspects of a User profile
    public void modifyGuestAccount(String newFirstName, String newLastName, String newUsername) {
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.username = newUsername;

        // Call to update database
        db.updateUserProfile(this);
    }

        /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public Response changePassword(String username, String currentPassword, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPassword = generatePasswordHash(newPassword);
        return db.updatePassword(username, currentPassword, hashedPassword);
    }
}
