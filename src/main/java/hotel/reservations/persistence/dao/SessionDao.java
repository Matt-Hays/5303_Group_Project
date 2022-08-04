package hotel.reservations.persistence.dao;


import hotel.reservations.models.user.User;

import java.util.UUID;

public interface SessionDao {
    public UUID createSession(User user);
    public boolean validateSession(UUID sessionId);
    public User getSessionUser(UUID sessionID);
    public void destroySessionById(UUID sessionID);
}
