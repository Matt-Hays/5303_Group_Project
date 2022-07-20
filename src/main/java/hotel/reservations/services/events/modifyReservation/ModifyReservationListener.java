package hotel.reservations.services.events.modifyReservation;

import hotel.reservations.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyReservationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String reservationId = e.getActionCommand();
        AppController.modifyReservation(reservationId);
    }
}
