package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.*;

public class HotelReservationGUI extends JFrame {
    public JPanel cards; //a panel that uses CardLayout

    public HotelReservationGUI() {
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
}