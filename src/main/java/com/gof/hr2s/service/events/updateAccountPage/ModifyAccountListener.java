package com.gof.hr2s.service.events.updateAccountPage;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyAccountListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.updateUserAccount();
        System.out.println("I do something");
    }
}
