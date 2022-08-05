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

/**
 * Provides a login interface to accept the user's username and password.
 * Return the user's to the Home Page upon successful login.
 * Displays an error message to the user on login failure.
 */
public class LoginPanel extends ThemedPanel {
    private Frame frame;
    private JLabel pageHeader;
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnLogin;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousMessage;

    public LoginPanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());
        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);
        btnBack = new RoundedButton("Back");
        btnLogin = new RoundedButton("Login");
        pageHeader = new JLabel("<html><h1 style='color:white; font-size:24; font-weight:bold'>Login</h1></html>");

        fillLayout();

        /**
         * Login ActionListener. Requests the login function from the controller. Returns an error message to the user
         * on login failure. Accepts and sets the Session object to the view for cached use of the User object, prepares
         * the Home Page for a logged-in user, changes the page to the Home page, and clears all text fields on the
         * login page.
         */
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasPreviousMessage) clearMessage();
                Session session = getFrame().getAppController().logIn(getUsernameField(), getPasswordField());
                if(session == null) displayMessage("Login attempt failed. Please try again.", "red");
                else {
                    getFrame().setSession(session);
                    getFrame().getHomePanel().loggedInDisplay();
                    getFrame().changeScreen("home");
                    clearTextFields();
                }
            }
        });

        /**
         * Returns the user to the Home page.
         * Clears the text fields.
         */
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("home");
                clearTextFields();
            }
        });
    }

    /**
     * Resets JTextFields to empty strings. (Clears the text fields)
     */
    private void clearTextFields(){
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Given a message to display and a desired HTML standard named color, display the message in the color.
     * @param message The message to display.
     * @param color The standard HTML named color to display the message.
     */
    public void displayMessage(String message, String color){
        gbc.gridy++;
        gbc.insets = new Insets(24,0,0,0);
        add(new JLabel("<html><p style='color:" + color + "'>" + message + "</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    /**
     * Clears the previous message so that multiple messages do not overwhelm the layout.
     */
    private void clearMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    /**
     * Provide the initial grid structure for the Login Page.
     */
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

    /**
     * Set the JFrame.
     * @param frame The parent JFrame object.
     */
    private void setFrame(Frame frame){
        this.frame = frame;
    }

    /**
     * @return The parent JFrame object.
     */
    private Frame getFrame() {
        return frame;
    }

    /**
     * Standard getters and setters follow through the end of the file.
     */
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
