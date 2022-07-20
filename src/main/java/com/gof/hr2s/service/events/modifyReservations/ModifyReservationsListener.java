package com.gof.hr2s.service.events.modifyReservations;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyReservationsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmds = e.getActionCommand();
        AppController.modifyReservationPage(cmds);
    }
}
