package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UserLogin extends JPanel {
    private JPanel homePanel;
    private JTextField usernameField;
    private JLabel input1Txt;
    private JButton loginBtn;
    private JPasswordField passwordField;
    private JLabel panelTitle;
    private JLabel panelSubtitle;
    private JButton registerBtn;

    public UserLogin() {
        this.add(homePanel);
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getInput1Txt() {
        return input1Txt.getText();
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    void addLoginListener(ActionListener listenForLoginBtn) {
        loginBtn.addActionListener(listenForLoginBtn);
    }

    void addRegistrationListener(ActionListener listenForRegistrationBtn) {
        registerBtn.addActionListener(listenForRegistrationBtn);
    }
}