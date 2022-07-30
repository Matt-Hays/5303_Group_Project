package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Clerk;
import hotel.reservations.models.user.User;
import hotel.reservations.services.UserDAO.IUserDAO;

import java.util.Set;
import java.util.UUID;

/**
 * Provides service into the User.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public class UserService implements Service<User, UUID>, AuthService, AdminService {
    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findById(UUID id) {
        return null;
    }

    @Override
    public Set<User> findAll() {
        return null;
    }

    @Override
    public User save(User object) {
        return null;
    }

    @Override
    public User create(User object) {
        return null;
    }

    @Override
    public void delete(User object) {

    }

    @Override
    public Session logIn(User user, char[] password) {
        return null;
    }

    @Override
    public void logOut(Session session) {

    }

    @Override
    public void resetPassword(User user, char[] currentPassword, char[] newPassword) {

    }

    @Override
    public Clerk createClerk(Session session, Clerk clerk) {
        return null;
    }

    @Override
    public Report viewReport(Session session) {
        return null;
    }

    @Override
    public Status viewStatus(Session session) {
        return null;
    }
}
