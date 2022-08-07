/**
 * @file Runner.java
 * @brief The application's starting point and configuration.
 * @author Matthew Hays
 * @dependencies none
 */

package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.controller.AppControllerImpl;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.views.frame.FrameImpl;

import javax.swing.*;

public class Runner {
    public static void main(String[] args) throws NoSuchMethodException {
        // Instantiate Persistence Layer - Passive Object
        DatabaseImpl db = new DatabaseImpl();

        // Instantiate Application Layer - Passive Object
        AppController applicationController = new AppControllerImpl(db);

        // Start Java Swing as an Active Component with its own thread of control.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instantiate Views Layer - Active Object
                new FrameImpl(applicationController);
            }
        });
    }
}