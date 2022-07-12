package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.service.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import static com.gof.hr2s.service.HotelAuth.generatePasswordHash;

public class Clerk extends User{

    public Clerk(UUID userId, String username, String firstName, String lastName) {
        super(userId, Account.CLERK, username, firstName, lastName);
        db = Database.Database();
    }

    public Response updateRoom (Room room) {
        return db.updateRoom(room);
    }

    /**
     * Attempts to lookup a guest in the db based on username
     * @param username
     * @return Guest instance on success, null on fail
     */
    public Guest getUser(String username) {
        Object obj = db.getUser(username);
        if (obj instanceof Guest) {
            return (Guest)obj;
        } else {
            return null;
        }
    }

    public Response setCustomer(String username) {
        Guest guest = getUser(username);
        if (guest != null) {
            super.setCustomer(guest);
            return Response.SUCCESS;
        }
        return Response.FAILURE;
    }
}