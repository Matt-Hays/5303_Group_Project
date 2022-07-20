package hotel.reservations.views;

import hotel.reservations.controller.ReservationHandler;

public interface IGuiFrame {
    public ReservationHandler getReservationHandler();
    public void changeView(String panelName);
}
