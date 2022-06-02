package com.gof.hr2s.ui;

import com.gof.hr2s.utils.HotelAuth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class GuestRegistration extends JFrame {
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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GuestRegistration guest = new GuestRegistration();
                    guest.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GuestRegistration() {
        setContentPane(registrationPanel);
        setTitle("Hotel Reservation System");
        setSize(600, 600);
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int type = 0;
                String username = usernameField.getText();
                String hashed_password;
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                int active = 1;

                try {
                    // Obtain db connection
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:hr2s.sqlite");

                    // Build the query
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO user" +
                            " (TYPE, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ACTIVE) " +
                            "values (?,?,?,?,?,?)");
                    ps.setInt(1, type);
                    ps.setString(2, username);

                    // Hash user input password to store inside the db
                    hashed_password = HotelAuth.generatePasswordHash(String.valueOf(passwordField.getPassword()));
                    ps.setString(3, hashed_password);
                    ps.setString(4, fName);
                    ps.setString(5, lName);
                    ps.setInt(6, active);

                    // Execute the query
                    ps.executeUpdate();

                    // Close the db connection
                    conn.close();

                } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                }

                dispose();
                // Display the Login Panel
                UserLogin loginScreen = new UserLogin();
                loginScreen.setVisible(true);
            }
        });
    }
}
