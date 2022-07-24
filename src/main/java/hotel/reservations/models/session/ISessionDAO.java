package hotel.reservations.models.session;


import hotel.reservations.models.user.IUserDAO;

import java.util.UUID;

public interface ISessionDAO {
    public void createSession(IUserDAO user);
    public boolean validateSession(UUID sessionId);
    public void destroySession(UUID sessionID);
}
