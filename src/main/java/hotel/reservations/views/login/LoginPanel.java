package hotel.reservations.views.login;

import hotel.reservations.models.session.Session;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedPasswordField;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class LoginPanel extends ThemedPanel {
    private Frame frame;
    private JLabel pageHeader;
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnLogin;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousErrorMessage;

    public LoginPanel(Frame frame){
        setFrame(frame);

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
                if (hasPreviousErrorMessage) clearErrorMessage();
                Session session = getFrame().getAppController().logIn(getUsernameField(), getPasswordField());
                if(session == null) displayErrorMessage();
                else {
                    getFrame().setSession(session);
                    getFrame().getHomePanel().loggedInDisplay();
                    getFrame().changeScreen("home");
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousErrorMessage) clearErrorMessage();
                getFrame().changeScreen("home");
            }
        });
    }

    private void displayErrorMessage(){
        gbc.gridy++;
        gbc.insets = new Insets(24,0,0,0);
        add(new JLabel("<html><p style='color:red'>Login attempt failed. Please try again.</p></html>"), gbc);
        this.hasPreviousErrorMessage = true;
        revalidate();
        repaint();
    }

    private void clearErrorMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousErrorMessage = false;
        revalidate();
        repaint();
    }

    private void fillLayout(){
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

    private void setFrame(Frame frame){
        this.frame = frame;
    }

    private Frame getFrame() {
        return frame;
    }

    public JLabel getPageHeader() {
        return pageHeader;
    }

    public void setPageHeader(JLabel pageHeader) {
        this.pageHeader = pageHeader;
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.setText(usernameField);
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    public RoundedButton getBtnBack() {
        return btnBack;
    }

    public void setBtnBack(RoundedButton btnBack) {
        this.btnBack = btnBack;
    }

    public RoundedButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(RoundedButton btnLogin) {
        this.btnLogin = btnLogin;
    }
}
