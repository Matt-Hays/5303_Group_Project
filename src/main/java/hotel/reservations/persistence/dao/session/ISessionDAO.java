package hotel.reservations.persistence.dao.session;


import hotel.reservations.models.user.User;

import java.util.UUID;

public interface ISessionDAO {
    public UUID createSession(User user);
    public String validateSession(UUID sessionId);
    public User getSessionUser(UUID sessionID);
    public void destroySession(UUID sessionID);
}
