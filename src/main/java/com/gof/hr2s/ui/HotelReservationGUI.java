package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class HotelReservationGUI implements ItemListener {

    JPanel cards; //a panel that uses CardLayout

    public void addComponentToPane(Container pane) {
        //Create the "cards".
        UserLogin loginView = new UserLogin();
        JPanel loginPanel = loginView.getHomePanel();

        GuestRegistration registrationView = new GuestRegistration();
        JPanel registrationPanel = registrationView.getRegistrationPanel();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, "login");
        cards.add(registrationPanel, "registration");

        pane.add(cards, BorderLayout.CENTER);
    }


    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    private static void createHotelReservationGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        HotelReservationGUI gui = new HotelReservationGUI();
        gui.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

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
                createHotelReservationGUI();
            }
        });
    }
}
