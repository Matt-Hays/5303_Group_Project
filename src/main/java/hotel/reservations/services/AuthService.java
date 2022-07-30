package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;

public interface AuthService {
    Session logIn(User user, char[] password);
    void logOut(Session session);
    void resetPassword(User user, char[] currentPassword, char[] newPassword);
}
