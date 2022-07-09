package com.gof.hr2s.ui;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.models.Account;
import com.gof.hr2s.service.HotelAuth;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class GuestRegistration {
    private JLabel panelTitle;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fNameField;
    private JLabel lNameTxt;
    private JLabel fNameTxt;
    private JLabel pwTxt;
    private JTextField lNameField;
    private JButton registerBtn;
    private JPanel registrationPanel;
    Database db = null;


    public GuestRegistration(JFrame appFrame) {
        appFrame.setContentPane(registrationPanel);
        appFrame.invalidate();
        appFrame.validate();
        db = Database.Database();


        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account type = Account.CUSTOMER;
                String username = usernameField.getText();
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                boolean active = true;

                SwingWorker worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {

                        // Hash user input password to store inside the db
                        String hashed_password = HotelAuth.generatePasswordHash(String.valueOf(passwordField.getPassword()));
                        db.insertUser(type, username, hashed_password, fName, lName, active);

                        return null;
                    }
                };
                worker.execute();

                // Display the Login Panel
                new UserLogin(appFrame);
            }
        });
    }
}