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
    private JTextField address1;
    private JTextField address2;
    private JTextField city;
    private JTextField state;
    private JTextField zip;

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

    public String getAddress1() {
        return address1.getText();
    }

    public String getAddress2() {
        return address2.getText();
    }

    public String getCity() {
        return city.getText();
    }

    public String getState() {
        return state.getText();
    }

    public String getZip() {
        return zip.getText();
    }

    public void addRegisterListener(ActionListener listenForRegisterBtn) {
        registerBtn.addActionListener(listenForRegisterBtn);
    }
}