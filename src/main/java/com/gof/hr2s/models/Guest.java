package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.service.Response;

import java.util.UUID;

public class Guest extends User{

    public Guest(UUID userId, String username) {
        super(userId, Account.GUEST, username);
        setCustomer(this);
    }
    public Guest(UUID userId, String username, String firstName, String lastName, String address1, String address2,
                 String city, String state, String zip) {
        this(userId, username);
        db = Database.Database();
    }

    public Guest(String username, String firstName, String lastName, String address1, String address2,
                 String city, String state, String zip) {
        super(UUID.randomUUID(), Account.GUEST, username, firstName, lastName, address1, address2, city, state, zip);
        db = Database.Database();
    }

    /**
     * Use standard setters and then call update to update db.
     * @return Response.SUCCESS, Response.FAILURE
     */
    public Response update() {
        return db.updateUserProfile(this);
    }

}