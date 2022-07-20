package hotel.reservations.services.events.loginPage;

import hotel.reservations.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.callNewPage("registration");
    }
}
