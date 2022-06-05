package com.gof.hr2s.user;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.utils.HotelAuth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Admin extends User{

    private String adminFirstName;
    private String adminLastName;
    Database db = null;

    public Admin(int userId, Account accountType, String username, String firstName, String lastName) {
        super(userId, accountType.ADMIN, username, firstName, lastName);
        this.adminFirstName = firstName;
        this.adminLastName = lastName;
    }

    /**
     * This creates any type of User account (Guest, Clerk, Admin) with a set Username and default password.
     * @param accountType
     * @param newUsername
     * @param newFirstName
     * @param newLastName
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void createUser(Account accountType, String newUsername, String newFirstName, String newLastName) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String hashed_password = HotelAuth.generatePasswordHash("password123$");

        int active = 1;

        db.insertUser(accountType, newUsername, hashed_password, newFirstName, newLastName, active);

    }
}
