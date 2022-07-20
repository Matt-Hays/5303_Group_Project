package hotel.reservations.models.user;

import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;

import java.util.UUID;

public class Guest extends User {

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