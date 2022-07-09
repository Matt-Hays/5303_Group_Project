package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UserLogin extends JPanel {
    private JPanel homePanel;
    private JLabel input1Txt;
    private JLabel panelTitle;
    private JLabel panelSubtitle;
    private JButton loginBtn;
    private JButton registerBtn;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserLogin() {
        this.add(homePanel);
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    public void addLoginListener(ActionListener listenForLoginBtn) {
        loginBtn.addActionListener(listenForLoginBtn);
    }

    public void addRegistrationListener(ActionListener listenForRegistrationBtn) {
        registerBtn.addActionListener(listenForRegistrationBtn);
    }
}