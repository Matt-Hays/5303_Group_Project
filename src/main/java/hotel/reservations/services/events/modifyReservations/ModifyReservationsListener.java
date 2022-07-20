package hotel.reservations.services.events.modifyReservations;

import hotel.reservations.controllers.reservation.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyReservationsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmds = e.getActionCommand();
        AppController.modifyReservationPage(cmds);
    }
}
