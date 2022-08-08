/**
 * @file SessionServiceImpl.java
 * @author Matthew Hays
 * @brief The SessionService implementing object. Provides logic and routing for configuring data access object
 *        requests within the particular domain to build a response to a Service layer request.
 *        ** Currently unused.
 * @dependencies SessionDao.java
 */

package hotel.reservations.services.impls;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.dao.SessionDao;
import hotel.reservations.services.SessionService;

import java.util.UUID;

public class SessionServiceImpl implements SessionService {
    private final SessionDao sessionDao;

    public SessionServiceImpl(SessionDao sessionDAO) {
        this.sessionDao = sessionDAO;
    }

    @Override
    public Session createSession(User user) {
        return sessionDao.createSession(user);
    }

    @Override
    public boolean validateSession(UUID id) {
        return sessionDao.validateSession(id);
    }

    @Override
    public void destroySessionById(UUID id) {
        sessionDao.destroySessionById(id);
    }

    @Override
    public User getSessionUser(UUID id) {
        return sessionDao.getSessionUser(id);
    }
}
