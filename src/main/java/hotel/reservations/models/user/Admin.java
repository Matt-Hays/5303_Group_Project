package hotel.reservations.models.user;

import hotel.reservations.persistence.Database;
import hotel.reservations.services.authentication.HotelAuth;
import hotel.reservations.services.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Admin extends User {

    public Admin(UUID userId, String username, String firstName, String lastName) {
        super(userId, Account.ADMIN, username, firstName, lastName);
        db = Database.Database();
    }

    /**
     * This creates any type of User account (Guest, Clerk, Admin) with a set Username and default password.
     * @param accountType
     * @param username
     * @param firstName
     * @param lastName
     * @return Response.SUCCESS or Response.FAIL
     */
    public Response createUser(Account accountType, String username, String firstName, String lastName) {
        String hashed_password;

        try {
            hashed_password = HotelAuth.generatePasswordHash("password123$");
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            return Response.FAILURE;
        }

        if (accountType == Account.CLERK) {
            Clerk clerk = new Clerk(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(clerk, hashed_password);
        } else if (accountType == Account.ADMIN) {
            Admin admin = new Admin(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(admin, hashed_password);
        } else if (accountType == Account.GUEST) {
            Guest guest = new Guest(UUID.randomUUID(), username.toLowerCase(), firstName, lastName);
            return db.insertUser(guest, hashed_password);
        }

        return Response.FAILURE;
    }
}