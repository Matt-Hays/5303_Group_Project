package hotel.reservations;

import hotel.reservations.controller.ApplicationController;
import hotel.reservations.controller.PrimaryController;
import hotel.reservations.persistence.Database;
import hotel.reservations.views.controller.GuiFrame;
import hotel.reservations.views.controller.GuiHandler;

import javax.swing.*;

public class Runner {
    public static void main(String[] args) throws NoSuchMethodException {
        // Persistence Layer
        Database db = new Database();

        // Views Layer
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Application Layer Controller
                ApplicationController applicationController = new PrimaryController(db);
                // Views Layer Controller
                GuiHandler guiHandler = new GuiFrame(applicationController);
                // Associate Views with the ApplicationController
                applicationController.addViewsHandler(guiHandler);
            }
        });
    }
}