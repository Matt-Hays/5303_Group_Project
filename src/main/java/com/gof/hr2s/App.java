package com.gof.hr2s;

import com.gof.hr2s.ui.HotelReservationGUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);

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
