package com.gof.hr2s.service.events.modifyRooms;

import com.gof.hr2s.controller.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyRoomsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String roomId = e.getActionCommand();
        AppController.clerkModifyRoomScreen(roomId);
    }
}
