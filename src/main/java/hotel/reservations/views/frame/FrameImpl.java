package hotel.reservations.views.frame;

import hotel.reservations.controller.AppController;
import hotel.reservations.models.session.Session;
import hotel.reservations.views.admin.AdminPanel;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.reservation.ReservationPanel;
import hotel.reservations.views.search.SearchPanel;
import hotel.reservations.views.user.ResetGuestCredentials;
import hotel.reservations.views.user.UserPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The parent frame of all user interface pages (JPanel).
 * Provides page switching and access to additional pages to all children pages.
 * Additionally, stores the Session object for the user for caching of the User object and endpoint authorization.
 */
public class FrameImpl extends JFrame implements Frame {
    private AppController appController;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Session session;

    public FrameImpl(AppController appController) {
        setApplicationController(appController);

        setSize(850, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hotel Reservations");

        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        /**
         * Create all user interface pages (JPanel) and add them to the frame's card layout.
         */
        cardPanel.add(new HomePanel(this), "home");
        cardPanel.add(new LoginPanel(this), "login");
        cardPanel.add(new RegisterPanel(this), "register");
        cardPanel.add(new SearchPanel(this), "search");
        cardPanel.add(new UserPanel(this), "account");
        cardPanel.add(new AdminPanel(this), "admin");
        cardPanel.add(new ResetGuestCredentials(this), "reset-guest-credentials");

        add(cardPanel);

        setVisible(true);
    }

    /**
     * Given a requested screen name, change the current screen to the requested screen.
     * @param screenName The requested screen name.
     */
    @Override
    public void changeScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    /**
     * Standard getter and setter methods follow through the end of the file.
     * These methods allow pages to gain access to other pages as well as the controller.
     */

    private void setApplicationController(AppController appController){
        this.appController = appController;
    }

    @Override
    public AppController getAppController() {
        return appController;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setHomePanel(String userType) {

    }

    @Override
    public HomePanel getHomePanel() {
        return (HomePanel) cardPanel.getComponent(0);
    }

    @Override
    public LoginPanel getLoginPanel() {
        return (LoginPanel) cardPanel.getComponent(1);
    }

    @Override
    public ReservationPanel getReservationPanel() {
        return null;
    }

    @Override
    public AdminPanel getAdminPanel() {
        return null;
    }

    @Override
    public RegisterPanel getRegisterPanel() {
        return (RegisterPanel) cardPanel.getComponent(2);
    }

    @Override
    public SearchPanel getSearchPanel() {
        return (SearchPanel) cardPanel.getComponent(3);
    }

    @Override
    public UserPanel getUserPanel() {
        return (UserPanel) cardPanel.getComponent(4);
    }
}
