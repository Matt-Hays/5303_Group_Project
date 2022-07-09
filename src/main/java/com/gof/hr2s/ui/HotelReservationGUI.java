package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.*;

public class HotelReservationGUI extends JFrame {
    public JPanel cards; //a panel that uses CardLayout

    public HotelReservationGUI() {
        //Create and set up the window.
        setTitle("Hotel Reservation System");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        //Create and set up the content pane.
        addComponentToPane(getContentPane());

        //Display the window.
        pack();
        setVisible(true);
    }

    private void addComponentToPane(Container pane) {
        //Create the "cards".
        JPanel loginPanel = new UserLogin();
        JPanel registrationPanel = new GuestRegistration();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, "login");
        cards.add(registrationPanel, "registration");

        pane.add(cards, BorderLayout.CENTER);
    }

//    public static void main(String[] args) {
//
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new HotelReservationGUI();
//            }
//        });
//    }
}
