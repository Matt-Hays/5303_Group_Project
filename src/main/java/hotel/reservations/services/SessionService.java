/**
 * @file SessionService.java
 * @author Matthew Hays
 * @brief Provides an interface to interact with Session domain requests.
 * ** Currently unused.
 */

package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;

import java.util.UUID;

/**
 * The Session Service Interface.
 * Provides an interface into the Session domain.
 */
public interface SessionService {
    public Session createSession(User user);
    public boolean validateSession(UUID id);
    public void destroySessionById(UUID id);
    public User getSessionUser(UUID id);
}
