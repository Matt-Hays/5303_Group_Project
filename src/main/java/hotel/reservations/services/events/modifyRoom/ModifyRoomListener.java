package hotel.reservations.services.events.modifyRoom;

import hotel.reservations.controllers.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AppController.clerkModifyRoom();
    }
}
