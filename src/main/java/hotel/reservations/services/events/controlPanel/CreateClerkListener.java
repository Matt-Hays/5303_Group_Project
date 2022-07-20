package hotel.reservations.services.events.controlPanel;

import hotel.reservations.controllers.reservation.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateClerkListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.adminCreateClerkPage();
    }
}
