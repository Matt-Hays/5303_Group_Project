package hotel.reservations.services.events.controlPanel;

import hotel.reservations.controllers.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchRoomsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.searchRoomsPageSetup();
    }
}
