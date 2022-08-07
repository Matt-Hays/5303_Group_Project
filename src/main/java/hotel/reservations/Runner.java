package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.controller.AppControllerImpl;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.views.frame.FrameImpl;
import hotel.reservations.views.frame.Frame;

import javax.swing.*;

public class Runner {
    public static void main(String[] args) throws NoSuchMethodException {
        // Persistence Layer
        DatabaseImpl db = new DatabaseImpl();
        // Application Layer Controller
        AppController applicationController = new AppControllerImpl(db);

        // Views Layer
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Views Layer Controller
                Frame guiHandler = new FrameImpl(applicationController);
                // Associate Views with the ApplicationController
            }
        });
    }
}