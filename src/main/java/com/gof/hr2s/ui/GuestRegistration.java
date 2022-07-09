package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GuestRegistration extends JPanel {
    private JPanel registrationPanel;
    private JLabel panelTitle;
    private JLabel usernameLabel;
    private JLabel lNameTxt;
    private JLabel fNameTxt;
    private JLabel pwTxt;
    private JButton registerBtn;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fNameField;
    private JTextField lNameField;
//    Database db = null;


    public GuestRegistration() {
        this.add(registrationPanel);
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    public String getFirstNameField() {
        return fNameField.getText();
    }

    public String getLastNameField() {
        return lNameField.getText();
    }

    void addRegisterListener(ActionListener listenForRegisterBtn) {
        registerBtn.addActionListener(listenForRegisterBtn);
    }
}