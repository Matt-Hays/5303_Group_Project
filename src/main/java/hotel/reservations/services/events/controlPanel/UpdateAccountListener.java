package hotel.reservations.services.events.controlPanel;

import hotel.reservations.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAccountListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.callNewPage("update-account");
    }
}
