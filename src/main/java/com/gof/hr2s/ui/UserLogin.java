package com.gof.hr2s.ui;

import com.gof.hr2s.user.User;
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
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                try {
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:hr2s.sqlite");
                    PreparedStatement ps = conn.prepareStatement("SELECT password FROM user " +
                            "WHERE username=?;");
                    ps.setString(1, username);

                    ResultSet rs = ps.executeQuery();
                    rs.first();

                    if(HotelAuth.validatePassword(password, String.valueOf(rs))){
                        JOptionPane.showMessageDialog(loginBtn, "You have validated your password!");
                    }

                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


}
