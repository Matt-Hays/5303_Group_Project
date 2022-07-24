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

        // Application Layer Controller

        // Views Layer
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationController applicationController = new PrimaryController(db);
                GuiHandler guiHandler = new GuiFrame(applicationController);
                applicationController.addViewsHandler(guiHandler);
            }
        });

//        // Persistence Layer
//        // Controller
//        AppController controller = new AppController(userdao);
    }
}