package com.gof.hr2s.user;

import com.gof.hr2s.db.Database;

public class Admin extends User{

    private String adminFirstName;
    private String adminLastName;
    public String jobTitle;

    Database db = null;

    public Admin(int userId, Account accountType, String username, String firstName, String lastName, String jobTitle) {
        super(userId, accountType.ADMIN, username, firstName, lastName);
        this.adminFirstName = firstName;
        this.adminLastName = lastName;
        this.jobTitle = "Admin";
    }

    public void createClerk(int userId, Account accountType, String username, String newFirstName, String newLastName, String newJobTitle){
        super(userId, accountType.CLERK, username);
        this.setFirstName(newFirstName);
        this.setLastName(newLastName);
        this.jobTitle = "Clerk";
        db = Database.Database(this);

    }
}
