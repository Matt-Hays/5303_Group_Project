package hotel.reservations.services.events.searchRoomsPanel;

import hotel.reservations.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAvailableRoomsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.searchAvailableRooms();
    }
}
