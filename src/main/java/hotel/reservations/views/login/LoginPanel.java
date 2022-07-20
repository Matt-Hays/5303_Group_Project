package hotel.reservations.views.login;

import hotel.reservations.views.GuiHandler;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedPasswordField;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends ThemedJPanel {

    private GuiHandler guiHandler;
    private JLabel pageHeader;
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnLogin;

    public LoginPanel(GuiHandler guiHandler){
        setGuiHandler(guiHandler);

        setLayout(new GridBagLayout());
        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);
        btnBack = new RoundedButton("Back");
        btnLogin = new RoundedButton("Login");
        pageHeader = new JLabel("<html><h1 style='color:white; font-size:24; font-weight:bold'>Login</h1></html>");

        fillLayout();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().getReservationHandler().loginUser(getUsername(), getPassword());
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeView("home");
            }
        });
    }

    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0,8,16,0);
        add(pageHeader, gbc);
        gbc.gridwidth = GridBagConstraints.BOTH;
        gbc.gridy++;
        gbc.insets = new Insets(6,2,0,2);
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("<html><p style='color:white; font-size:14px'>Username:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:14px'>Password:</p></html> "), gbc);
        gbc.gridx = gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(14, 24, 0, 0);
        add(btnLogin, gbc);
        gbc.insets = new Insets(14, 128, 0, 4);
        add(btnBack, gbc);
    }

    private void setGuiHandler(GuiHandler guiHandler){
        this.guiHandler = guiHandler;
    }

    private GuiHandler getGuiHandler() {
        return guiHandler;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

}
