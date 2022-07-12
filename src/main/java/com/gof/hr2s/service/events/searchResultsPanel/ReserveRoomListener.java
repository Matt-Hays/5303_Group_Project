package com.gof.hr2s.service.events.searchResultsPanel;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReserveRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String roomId = e.getActionCommand();
        AppController.makeReservation(roomId);
    }
}
