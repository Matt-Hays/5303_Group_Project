package hotel.reservations.services.impls;

import hotel.reservations.models.session.Session;
import hotel.reservations.persistence.dao.SessionDao;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.dao.UserDao;
import hotel.reservations.persistence.Response;
import hotel.reservations.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final SessionDao sessionDao;

    public UserServiceImpl(UserDao userDAO, SessionDao sessionDAO) {
        this.userDao = userDAO;
        this.sessionDao = sessionDAO;
    }

    @Override
    public Session login(String username, char[] password) {
        User user;
        try {
            user = userDao.logIn(username, password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return sessionDao.createSession(user);
    }

    @Override
    public void logout(UUID id) {
        sessionDao.destroySessionById(id);
    }

    @Override
    public Session createUser(Account accountType, String username, char[] password, String fName, String lName,
                              String street, String state, String zipCode) {
        User user;
        try {
            user = userDao.createUser(accountType, username, password, fName, lName, street, state, zipCode);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return sessionDao.createSession(user);
    }

    @Override
    public void createClerk(String username, String firstName, String lastName, String street, String state, String zipCode) {
        userDao.createDefaultUser(Account.CLERK, username, firstName, lastName, street, state, zipCode);
    }

    @Override
    public User updateUser(UUID id, String newUsername, String firstName, String lastName, String street, String state,
                               String zipCode, boolean active) {
        Response res = userDao.updateUser(sessionDao.getSessionUser(id).getUserId(), newUsername, firstName, lastName, street,
                state, zipCode, active);
        User user = null;
        if(res == Response.SUCCESS) {
            user = userDao.getUserById(sessionDao.getSessionUser(id).getUserId());
            sessionDao.updateSessionUser(user);
        }
        return user;
    }

    @Override
    public void updatePassword(String username, char[] oldPassword, char[] newPassword) {
        userDao.changePassword(username, oldPassword, newPassword);
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteUserById(UUID id) {

    }
}
