package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.service.Response;

import java.util.UUID;

public class Guest extends User{

    public Guest(UUID userId, String username, String firstName, String lastName) {
        super(userId, Account.GUEST, username, firstName, lastName);
        db = Database.Database();
        setCustomer(this);
    }

    /**
     * Use standard setters and then call update to update db.
     * @return Response.SUCCESS, Response.FAILURE
     */
    public Response update() {
        return db.updateUserProfile(this);
    }

}