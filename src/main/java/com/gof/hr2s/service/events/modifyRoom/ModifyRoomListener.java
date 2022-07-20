package com.gof.hr2s.service.events.modifyRoom;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.clerkModifyRoom();
    }
}
