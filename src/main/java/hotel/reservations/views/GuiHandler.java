package hotel.reservations.views;

import hotel.reservations.controller.ReservationHandler;

public interface GuiHandler {
    public ReservationHandler getReservationHandler();
    public void changeView(String panelName);
}
