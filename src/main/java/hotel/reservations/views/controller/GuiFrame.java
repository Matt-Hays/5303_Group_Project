package hotel.reservations.views.controller;

import hotel.reservations.controller.AppController;
import hotel.reservations.views.admin.AdminPanel;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.reservation.ReservationPanel;
import hotel.reservations.views.search.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class GuiFrame extends JFrame implements GuiHandler{
    private AppController appController;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private UUID sessionCtx;

    public GuiFrame(AppController appController) {
        setApplicationController(appController);

        setSize(850, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hotel Reservations");

        // Create card layout
        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        // Create cards
        // Add cards to card layout
        cardPanel.add(new HomePanel(this), "home");
        cardPanel.add(new LoginPanel(this), "login");
        cardPanel.add(new RegisterPanel(this), "register");
        cardPanel.add(new SearchPanel(this), "search");

        // Add card panel to app frame.
        add(cardPanel);

        // Display app frame.
        setVisible(true);
    }

    private void setApplicationController(AppController appController){
        this.appController = appController;
    }

    @Override
    public void changeScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    @Override
    public AppController getAppController() {
        return appController;
    }

    @Override
    public void setSessionCtx(UUID sessionCtx) {
        this.sessionCtx = sessionCtx;
    }

    @Override
    public UUID getSessionCtx() {
        return sessionCtx;
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
}
