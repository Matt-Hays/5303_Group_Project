package hotel.reservations.models.session;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Session {
    private UUID id;
    private Object user;
    private Timestamp expiry;

    public Session(Object loggedInUser) {
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

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }
}