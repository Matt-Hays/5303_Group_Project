package hotel.reservations.services.events.searchResultsPanel;

import hotel.reservations.controllers.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReserveRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String btnParams = e.getActionCommand();
        AppController.makeReservation(btnParams);
    }
}
