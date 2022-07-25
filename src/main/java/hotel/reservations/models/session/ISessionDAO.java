package hotel.reservations.models.session;


import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;

import java.util.UUID;

public interface ISessionDAO {
    public UUID createSession(User user);
    public Account validateSession(UUID sessionId);
    public User getSessionUser(UUID sessionID);
    public void destroySession(UUID sessionID);
}
