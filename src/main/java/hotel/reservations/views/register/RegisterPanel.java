package hotel.reservations.views.register;

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

/**
 * The Register Panel of the user interface.
 * Provides the user with the functionality to crete a new user account.
 * Routes the user to the Home Page and displays a Welcome message to the user upon creation success.
 * Displays an error message to the user upon creation failure.
 */
public class RegisterPanel extends ThemedPanel {
    private Frame frame;
    private RoundedTextField usernameField, firstNameField, lastNameField, addressField, stateField, zipCodeField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnRegister;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousMessage;


    public RegisterPanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());

        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);
        firstNameField = new RoundedTextField(20);
        lastNameField = new RoundedTextField(20);
        addressField = new RoundedTextField(20);
        stateField = new RoundedTextField(2);
        zipCodeField = new RoundedTextField(5);
        btnBack = new RoundedButton("Back");
        btnRegister = new RoundedButton("Register");

        fillLayout();

        /**
         * Register ActionListener.
         * Requests the creation of a new user account.
         * Returns an error message to the user upon failure.
         * Saves the Session object to the view cache, redirects the user to the Home page and displays a welcome
         * message upon success.
         */
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();

                String username = getUsernameField();
                char[] password = getPasswordField();
                String firstName = getFirstNameField();
                String lastName = getLastNameField();
                String address = getAddressField();
                String state = getStateField();
                String zipCode = getZipCodeField();

                Session session = getFrame()
                        .getAppController()
                        .registerUser(username, password, firstName, lastName, address, state, zipCode);

                if(session == null) {
                    displayMessage("Registration attempt failed. Please try again.", "red");
                    return;
                };

                getFrame().setSession(session);
                getFrame().getHomePanel().displayMessage("Welcome to our hotel!", "green");
                getFrame().getHomePanel().loggedInDisplay();
                getFrame().changeScreen("home");
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("home");
            }
        });
    }

    /**
     * Given a message to display and a desired HTML standard named color, displays the message using the color.
     * @param message The message to display.
     * @param color The standard HTML named color used to display the message.
     */
    private void displayMessage(String message, String color){
        gbc.gridy++;
        gbc.insets = new Insets(24,0,0,0);
        add(new JLabel("<html><p style='color:" + color + "'>" + message + "</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    /**
     * Clears the previous message so that messages do not overwhelm the user interface.
     */
    private void clearMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    /**
     * Provides the initial grid structure of the Register Page.
     */
    private void fillLayout(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("<html><h1 style='color:white; font-weight:bold; " +
                "text-decoration:underline'>Register</h1></html>"));
        gbc.gridx = 0;
        gbc.gridy = 36;
        gbc.insets = new Insets(6,2,0,2);
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("<html><p style='color:white; font-size:12px'>Username:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>Password:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>First Name:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>Last Name:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>Address:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>City:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>State:</p></html> "), gbc);
        gbc.gridy++;
        add(new JLabel("<html><p style='color:white; font-size:12px'>Zip Code:</p></html> "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 36;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        add(firstNameField, gbc);
        gbc.gridy++;
        add(lastNameField, gbc);
        gbc.gridy++;
        add(addressField, gbc);
        gbc.gridy++;
        add(stateField, gbc);
        gbc.gridy++;
        add(zipCodeField, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(16, 24, 0, 0);
        add(btnRegister, gbc);
        gbc.insets = new Insets(16, 136, 0, 0);
        add(btnBack, gbc);
    }

    /**
     * Standard getter and setter methods follow through the end of the page.
     */
    private void setFrame(Frame frame){
        this.frame = frame;
    }

    private Frame getFrame(){
        return frame;
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.setText(usernameField);
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public void setFirstNameField(String firstNameField) {
        this.firstNameField.setText(firstNameField);
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public void setLastNameField(String lastNameField) {
        this.lastNameField.setText(lastNameField);
    }

    public String getAddressField() {
        return addressField.getText();
    }

    public void setAddressField(String addressField) {
        this.addressField.setText(addressField);
    }

    public String getStateField() {
        return stateField.getText();
    }

    public void setStateField(String stateField) {
        this.stateField.setText(stateField);
    }

    public String getZipCodeField() {
        return zipCodeField.getText();
    }

    public void setZipCodeField(String zipCodeField) {
        this.zipCodeField.setText(zipCodeField);
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

    public RoundedButton getBtnRegister() {
        return btnRegister;
    }

    public void setBtnRegister(RoundedButton btnRegister) {
        this.btnRegister = btnRegister;
    }
}
