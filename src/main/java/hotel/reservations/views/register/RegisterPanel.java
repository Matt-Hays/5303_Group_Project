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

/**
 * The Register Panel of the user interface.
 * Provides the user with the functionality to crete a new user account.
 * Routes the user to the Home Page and displays a Welcome message to the user upon creation success.
 * Displays an error message to the user upon creation failure.
 */
public class RegisterPanel extends ThemedPanel {
    private Frame frame;
    private JLabel username, password, fName, lName, street, state, zip, pageTitle;
    private RoundedTextField usernameField, firstNameField, lastNameField, streetField, stateField, zipCodeField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnRegister;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousMessage, isAdminMode;


    public RegisterPanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><h1 style='color:white; font-size:24px; font-weight:bold'>Register</h1></html>");
        username = new JLabel("<html><p style='color:white; font-size:12px'>Username:</p></html>");
        usernameField = new RoundedTextField(20);
        password = new JLabel("<html><p style='color:white; font-size:12px'>Password:</p></html>");
        passwordField = new RoundedPasswordField(20);
        fName = new JLabel("<html><p style='color:white; font-size:12px'>First Name:</p></html>");
        firstNameField = new RoundedTextField(20);
        lName = new JLabel("<html><p style='color:white; font-size:12px'>Last Name:</p></html>");
        lastNameField = new RoundedTextField(20);
        street = new JLabel("<html><p style='color:white; font-size:12px'>Street:</p></html>");
        streetField = new RoundedTextField(20);
        state = new JLabel("<html><p style='color:white; font-size:12px'>State:</p></html>");
        stateField = new RoundedTextField(20);
        zip = new JLabel("<html><p style='color:white; font-size:12px'>Zip Code:</p></html>");
        zipCodeField = new RoundedTextField(20);
        btnBack = new RoundedButton("Back");
        btnRegister = new RoundedButton("Register");

        fillLayout();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                if(isAdminMode) removeAdminMode();
                clearTextFields();
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

    public void removeAdminMode(){
        // Remove existing ActionListeners
        for(ActionListener al : btnRegister.getActionListeners()){
            btnRegister.removeActionListener(al);
        }

        // Reconfigure the initial layout including initial ActionListener(s).
        fillLayout();

        revalidate();
        repaint();
    }

    /**
     * Arranges the Display for an Admin
     */
    public void prepareAdminDisplay(){
        // Remove existing ActionListeners
        for(ActionListener al : btnRegister.getActionListeners()){
            btnRegister.removeActionListener(al);
        }

        // Remove the unnecessary fields and associated labels.
        if(passwordField != null && password != null){
            remove(passwordField);
            remove(password);
        }

        // Change the Register Button's text to match to use case.
        btnRegister.setText("Create Clerk");

        // Change the ActionListener to call create clerk instead of register user.
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = getUsernameField();
                String firstName = getFirstNameField();
                String lastName = getLastNameField();
                String street = getStreetField();
                String state = getStateField();
                String zip = getZipCodeField();

                getFrame().getAppController().createClerk(username, firstName, lastName, street, state, zip);
                getFrame().getHomePanel().displayMessage("Clerk has been created successfully!", "green");
                getFrame().changeScreen("home");
                removeAdminMode();
                clearTextFields();
            }
        });

        // Update the page to reflect that Admin Mode is now set.
        isAdminMode = true;
    }

    private void clearTextFields(){
        usernameField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetField.setText("");
        stateField.setText("");
        zipCodeField.setText("");
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
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(pageTitle, gbc);

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(username, gbc);
        gbc.gridx++;
        add(usernameField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(password, gbc);
        gbc.gridx++;
        add(passwordField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(fName, gbc);
        gbc.gridx++;
        add(firstNameField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(lName, gbc);
        gbc.gridx++;
        add(lastNameField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(street, gbc);
        gbc.gridx++;
        add(streetField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(state, gbc);
        gbc.gridx++;
        add(stateField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(zip, gbc);
        gbc.gridx++;
        add(zipCodeField, gbc);

        gbc.insets = new Insets(16, 0, 0, 0);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(btnRegister, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        add(btnBack, gbc);

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
                String address = getStreetField();
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

    public String getStreetField() {
        return streetField.getText();
    }

    public void setStreetField(String streetField) {
        this.streetField.setText(streetField);
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
