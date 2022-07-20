package hotel.reservations.services.events.modifyRooms;

import hotel.reservations.controllers.AppController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyRoomsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String roomId = e.getActionCommand();
        AppController.clerkModifyRoomScreen(roomId);
    }
}
