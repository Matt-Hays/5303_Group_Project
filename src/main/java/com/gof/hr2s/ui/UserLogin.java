package com.gof.hr2s.ui;

import com.gof.hr2s.utils.HotelAuth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class UserLogin extends JFrame {
    private JPanel homePanel;
    private JTextField usernameField;
    private JLabel input1Txt;
    private JButton loginBtn;
    private JPasswordField passwordField;
    private JLabel panelTitle;
    private JLabel panelSubtitle;
    private JButton registerBtn;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UserLogin user = new UserLogin();
                    user.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserLogin() {
        setContentPane(homePanel);
        setTitle("Hotel Reservation System");
        setSize(600, 600);
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        // If the LOGIN button is clicked
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get Input Field vlues
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                try {
                    // Connect to db
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:hr2s.sqlite");

                    // Build the query
                    PreparedStatement ps = conn.prepareStatement("SELECT password FROM user WHERE username=?;");
                    ps.setString(1, username);

                    // Execute the query
                    ResultSet rs = ps.executeQuery();

                    // If password matches or if password doesn't match
                    if (HotelAuth.validatePassword(password, rs.getString("password"))) {
                        JOptionPane.showMessageDialog(loginBtn, "You have validated your password!");
                    } else {
                        JOptionPane.showMessageDialog(loginBtn,
                                "The username and/or password you entered was incorrect!");
                    }

                    // Close the db connection
                    conn.close();

                } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // If the REGISTER button is clicked
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Display the registration panel
                GuestRegistration registration = new GuestRegistration();
                registration.setVisible(true);
            }
        });
    }


}
