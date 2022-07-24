package hotel.reservations.views;

public interface GuiHandler {
    public void changeScreen(String screenName);

    public void getApplicationHandler();

    public void setHomePanel(String userType);

    public void getLoginPanel();

    public void getReservationPanel();

    public void getAdminPanel();

    public void getRegisterPanel();

    public void getSearchPanel();
}
