package hotel.reservations.services.events.registrationPage;

import hotel.reservations.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            AppController.registerUser();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}
