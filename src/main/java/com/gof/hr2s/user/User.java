package com.gof.hr2s.user;

import com.gof.hr2s.utils.HotelAuth;

public class User {
    public final int userId;
    public final Account accountType;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active = true;

    public User(int userId, Account accountType, String username) {
        this.userId = userId;
        this.accountType = accountType;
        this.username = username;
    }

    public User(int userId, Account accountType, String username, String firstName, String lastName) {
        this(userId, accountType, username);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account getAccountType() {
        return this.accountType;
    }

    public void setUserame(String username) {
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
}
