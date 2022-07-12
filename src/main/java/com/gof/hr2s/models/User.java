package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.service.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.UUID;

import static com.gof.hr2s.service.HotelAuth.generatePasswordHash;

abstract class User {
    public final UUID userId;
    public final Account accountType;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active = true;
    private Guest customer = null;
    Database db;

    public User(UUID userId, Account accountType, String username) {
        this.userId = userId;
        this.accountType = accountType;
        this.username = username;
    }

    public User(UUID userId, Account accountType, String username, String firstName, String lastName) {
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

    /**
     * updates a user's password
     * @param username username of the user
     * @param currentPassword current password of the user
     * @param newPassword new password of the user
     */
    public Response changePassword(String username, String currentPassword, String newPassword) {

        try {
            return db.updatePassword(username, currentPassword, newPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

        return Response.FAILURE;
    }

    public Reservation createReservation(LocalDate arrival, LocalDate departure, Room room) {
        if (null != customer) {
            return new Reservation(customer, room, LocalDate.now(), arrival, departure, ReservationStatus.AWAITING);
        }
        return null;
    }

    public Response setCustomer(Guest guest) {
        if (guest != null) {
            customer = guest;
            return Response.SUCCESS;
        }
        return Response.FAILURE;
    }


    public Guest getCustomer() {
        return customer;
    }
}