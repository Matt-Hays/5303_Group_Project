package com.gof.hr2s.service.events.controlPanel;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchRoomsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.searchAvailableRooms();
    }
}