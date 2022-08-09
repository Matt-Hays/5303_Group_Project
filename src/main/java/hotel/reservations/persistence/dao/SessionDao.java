/**
 * @file SessionDao.java
 * @author Matthew Hays
 * @brief Provides access to the Session domain used to authenticate users.
 */

package hotel.reservations.persistence.dao;


import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Response;

import java.util.UUID;

public interface SessionDao {
    public Session createSession(User user);
    public boolean validateSession(UUID sessionId);
    public User getSessionUser(UUID sessionID);
    public Response destroySessionById(UUID sessionID);
    public Response updateSessionUser(User user);
}
