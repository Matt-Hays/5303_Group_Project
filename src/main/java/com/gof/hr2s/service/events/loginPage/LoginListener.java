package com.gof.hr2s.service.events.loginPage;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            AppController.login();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}
