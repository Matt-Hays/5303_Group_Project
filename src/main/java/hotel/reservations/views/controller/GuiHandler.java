package hotel.reservations.views.controller;

import hotel.reservations.controller.ApplicationController;

import java.util.UUID;

public interface GuiHandler {
    public void changeScreen(String screenName);

    public ApplicationController getApplicationController();

    public void setSessionCtx(UUID sessionCtx);
    public UUID getSessionCtx();

    public void setHomePanel(String userType);

    public void getLoginPanel();

    public void getReservationPanel();

    public void getAdminPanel();

    public void getRegisterPanel();

    public void getSearchPanel();
}
