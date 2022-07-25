package hotel.reservations.views.controller;

import hotel.reservations.controller.ApplicationController;
import hotel.reservations.views.admin.AdminPanel;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.reservation.ReservationPanel;
import hotel.reservations.views.search.SearchPanel;

import java.util.UUID;

public interface GuiHandler {
    public void changeScreen(String screenName);

    public ApplicationController getApplicationController();

    public void setSessionCtx(UUID sessionCtx);
    public UUID getSessionCtx();

    public void setHomePanel(String userType);

    public HomePanel getHomePanel();

    public LoginPanel getLoginPanel();

    public ReservationPanel getReservationPanel();

    public AdminPanel getAdminPanel();

    public RegisterPanel getRegisterPanel();

    public SearchPanel getSearchPanel();
}
