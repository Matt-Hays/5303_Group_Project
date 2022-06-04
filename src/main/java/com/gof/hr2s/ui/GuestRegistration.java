package com.gof.hr2s.ui;

import com.gof.hr2s.utils.HotelAuth;

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

    public GuestRegistration(JFrame appFrame) {
        appFrame.setContentPane(registrationPanel);
        appFrame.invalidate();
        appFrame.validate();

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int type = 0;
                String username = usernameField.getText();
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                int active = 1;

                SwingWorker worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Obtain db connection
                        Connection conn = DriverManager.getConnection("jdbc:sqlite:hr2s.sqlite");

                        // Build the query
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO user" +
                                " (TYPE, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ACTIVE) " +
                                "values (?,?,?,?,?,?)");
                        ps.setInt(1, type);
                        ps.setString(2, username);

                        // Hash user input password to store inside the db
                        String hashed_password = HotelAuth.generatePasswordHash(String.valueOf(passwordField.getPassword()));
                        ps.setString(3, hashed_password);
                        ps.setString(4, fName);
                        ps.setString(5, lName);
                        ps.setInt(6, active);

                        // Execute the query
                        ps.executeUpdate();
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
