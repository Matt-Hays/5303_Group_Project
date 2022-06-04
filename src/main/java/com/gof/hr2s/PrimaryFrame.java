package com.gof.hr2s;

import com.gof.hr2s.ui.UserLogin;

import javax.swing.*;

public class PrimaryFrame {

    public PrimaryFrame() {
        JFrame appFrame = generateFrame();
        addInitialPanel(appFrame);
    }

    private JFrame generateFrame() {
        JFrame appFrame = new JFrame("Hotel Reservation System");
        appFrame.setSize(600, 600);
        appFrame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        appFrame.setVisible(true);
        return appFrame;
    }

    private void addInitialPanel(JFrame appFrame){
        new UserLogin(appFrame);
    }
}
