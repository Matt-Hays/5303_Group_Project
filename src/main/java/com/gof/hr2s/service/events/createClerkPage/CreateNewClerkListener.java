package com.gof.hr2s.service.events.createClerkPage;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CreateNewClerkListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            AppController.createANewClerk();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}
