package hotel.reservations.models.session;


import hotel.reservations.models.user.User;

import java.util.UUID;

public interface ISessionDAO {
    public UUID createSession(User user);
    public boolean validateSession(UUID sessionId);
    public void destroySession(UUID sessionID);
}
