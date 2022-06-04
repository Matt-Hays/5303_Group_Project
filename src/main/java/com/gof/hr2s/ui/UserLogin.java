package com.gof.hr2s.ui;

import com.gof.hr2s.utils.HotelAuth;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static javax.swing.SwingUtilities.isEventDispatchThread;

public class UserLogin extends JPanel {
    private JPanel homePanel;
    private JTextField usernameField;
    private JLabel input1Txt;
    private JButton loginBtn;
    private JPasswordField passwordField;
    private JLabel panelTitle;
    private JLabel panelSubtitle;
    private JButton registerBtn;

    public UserLogin(JFrame appFrame) {
        appFrame.setContentPane(homePanel);
        appFrame.invalidate();
        appFrame.validate();
        // If the LOGIN button is clicked
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get Input Field values
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                SwingWorker worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        // Connect to db
                        Connection conn = DriverManager.getConnection("jdbc:sqlite:hr2s.sqlite");
                        // Build the query
                        PreparedStatement ps = conn.prepareStatement("SELECT password FROM user WHERE username=?;");
                        ps.setString(1, username);

                        // Execute the query
                        ResultSet rs = ps.executeQuery();
                        if (!rs.isClosed()) {
                            String hashPw = rs.getString("password");
                            if (!hashPw.trim().isEmpty()) {
                                return HotelAuth.validatePassword(password, hashPw);
                            }
                        }
                        return false;
                    }
                };
                worker.execute();

                try {
                    Boolean isAuthenticated = (Boolean) worker.get();
                    if (isAuthenticated) {
                        JOptionPane.showMessageDialog(loginBtn, "You have validated your password!");
                    } else {
                        JOptionPane.showMessageDialog(loginBtn, "Please renter your username / or password.");
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // If the REGISTER button is clicked
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the registration panel
                new GuestRegistration(appFrame);
            }
        });
    }


}
