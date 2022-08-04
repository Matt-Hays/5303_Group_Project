package hotel.reservations.views.frame;

import hotel.reservations.controller.AppController;
import hotel.reservations.models.session.Session;
import hotel.reservations.views.admin.AdminPanel;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.reservation.ReservationPanel;
import hotel.reservations.views.search.SearchPanel;
import hotel.reservations.views.user.UserPanel;

import java.util.UUID;

/**
 * The parent frame (JFrame) interface.
 * Provides an interface such that pages can access additional pages and the controller.
 */
public interface Frame {
    public void changeScreen(String screenName);

    public AppController getAppController();

    public void setSession(Session session);
    public Session getSession();

    public void setHomePanel(String userType);

    public HomePanel getHomePanel();

    public LoginPanel getLoginPanel();

    public ReservationPanel getReservationPanel();

    public AdminPanel getAdminPanel();

    public RegisterPanel getRegisterPanel();

    public SearchPanel getSearchPanel();

    public UserPanel getUserPanel();
}
