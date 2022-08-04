package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;

import java.util.UUID;

public interface SessionService {
    public Session createSession(User user);
    public boolean validateSession(UUID id);
    public void destroySessionById(UUID id);
    public User getSessionUser(UUID id);
}
