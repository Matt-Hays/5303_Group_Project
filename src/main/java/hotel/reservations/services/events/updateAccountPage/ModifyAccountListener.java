package hotel.reservations.services.events.updateAccountPage;

import hotel.reservations.controllers.reservation.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyAccountListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.updateUserAccount();
    }
}
