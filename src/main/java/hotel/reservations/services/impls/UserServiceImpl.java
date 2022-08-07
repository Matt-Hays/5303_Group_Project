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

/**
 * Provides domain layer access to the controller.
 * The UserService Layer Object provides access into the User Domain.
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final SessionDao sessionDao;

    public UserServiceImpl(UserDao userDAO, SessionDao sessionDAO) {
        this.userDao = userDAO;
        this.sessionDao = sessionDAO;
    }

    /**
     * Requests the login function from the UserDao and if the login is successful,
     * requests the create session function from the SessionDao.
     * @param username The given username.
     * @param password The given password.
     * @return A session object.
     */
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

    /**
     * Requests the destroy session by id function from the SessionDao.
     * @param id The user's session id.
     */
    @Override
    public Response logout(UUID id) {
        return sessionDao.destroySessionById(id);
    }

    /**
     * Requests the create user function from the UserDao. If successful, requests
     * the create session function from the SessionDao.
     * @param accountType The user's account type. Enum: {GUEST, CLERK, ADMIN}
     * @param username The user's requested username.
     * @param username The user's requested username.
     * @param password The user's requested password.
     * @param fName The user's given first name.
     * @param lName The user's given last name.
     * @param street The user's given street address.
     * @param state The user's given state.
     * @param zipCode The user's given zip code.
     * @return A Session object.
     */
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

    /**
     * Requests the create default user function from the UserDao.
     * @param username The requested username.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param street The user's street address.
     * @param state The user's state.
     * @param zipCode The user's zip code.
     */
    @Override
    public Response createClerk(String username, String firstName, String lastName, String street, String state, String zipCode) {
        return userDao.createDefaultUser(Account.CLERK, username, firstName, lastName, street, state, zipCode);
    }

    /**
     * Requests the update user function from the UserDao. If the request is successful, requests an updated copy
     * of the User object from the UserDao and requests a Session object from the SessionDao.
     * @param id The logged in user's session id.
     * @param newUsername The requested username.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param street The user's street address.
     * @param state The user's state.
     * @param zipCode The user's zip code.
     * @param active The user's account status. Enum: {ACTIVE, INACTIVE}
     * @return The updated User object.
     */
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

    /**
     * Requests the change password function from the UserDao.
     * @param username The user's username.
     * @param oldPassword The user's current (old) password.
     * @param newPassword The user's requested new password.
     */
    @Override
    public Response updatePassword(String username, char[] oldPassword, char[] newPassword) {
        return userDao.changePassword(username, oldPassword, newPassword);
    }

    @Override
    public Response resetGuestPassword(UUID sessionId, String username) {
        if(sessionDao.getSessionUser(sessionId).getAccountType().equals(Account.ADMIN))
            return userDao.resetGuestPassword(username);
        return Response.FAILURE;
    }

    @Override
    public Response deleteUser(User user) {
        return Response.FAILURE;
    }

    @Override
    public Response deleteUserById(UUID id) {
        return Response.FAILURE;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
