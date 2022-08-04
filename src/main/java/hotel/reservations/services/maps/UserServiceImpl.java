package hotel.reservations.services.maps;

import hotel.reservations.models.session.ISessionDAO;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.daos.UserDAO.IUserDAO;
import hotel.reservations.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final IUserDAO userDAO;
    private final ISessionDAO sessionDAO;

    public UserServiceImpl(IUserDAO userDAO, ISessionDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    @Override
    public UUID login(String username, char[] password) {
        User user;
        try {
            user = userDAO.logIn(username, password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return sessionDAO.createSession(user);
    }

    @Override
    public Session logout(Session session) {
        return null;
    }

    @Override
    public UUID createUser(Account accountType, String username, char[] password, String fName, String lName, String street, String state, String zipCode) {
        User user;
        try {
            user = userDAO.createUser(accountType, username, password, fName, lName, street, state, zipCode);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return sessionDAO.createSession(user);
    }

    @Override
    public User updateUser(User updatedUser) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteUserById(UUID id) {

    }
}
