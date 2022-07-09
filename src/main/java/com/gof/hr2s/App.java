package com.gof.hr2s;

import com.gof.hr2s.ui.HotelReservationGUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Views
                HotelReservationGUI gui = new HotelReservationGUI();
                // Models
                // Controller
                // Persistence Layer
            }
        });
    }
}
