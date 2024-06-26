/**
 * @file Session.java
 * @author Matthew Hays
 * @brief The Session domain object. Provides the application with a data structure that's presence or
 *        absence in application memory can assist in authorization and state storage of the User.
 */

package hotel.reservations.models.session;


import hotel.reservations.models.user.User;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Session {
    private UUID id;
    private User user;
    private Timestamp expiry;

    public Session(User loggedInUser) {
        this.id = UUID.randomUUID();
        this.user = loggedInUser;
        this.expiry = generateExpiry();
    }

    // Set session expiration
    private Timestamp generateExpiry() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((timestamp.getTime()));
        cal.add(Calendar.HOUR, 8);
        return new Timestamp(cal.getTime().getTime());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }
}