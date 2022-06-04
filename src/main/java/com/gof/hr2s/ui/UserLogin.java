package com.gof.hr2s.ui;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.room.Room;
import com.gof.hr2s.user.User;
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
    Database db = null;

    public UserLogin(JFrame appFrame) {
        appFrame.setContentPane(homePanel);
        appFrame.invalidate();
        appFrame.validate();
        db = Database.Database();

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

                        String hashPw = db.getPassword(username);
                        if (hashPw != null && !hashPw.trim().isEmpty()) {
                            return HotelAuth.validatePassword(password, hashPw);
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
