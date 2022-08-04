package hotel.reservations.views.user;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
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
 * The User Page.
 * Provides the user an interface to view and update their profile information as well as view their upcoming reservations.
 */
public class UserPanel extends ThemedPanel {
    private final Frame frame;
    private JLabel fName, lName, street, state, zip, clerkOldPwLabel, clerkNewPwLabel;
    private RoundedTextField fNameText, lNameText, streetText, stateText, zipText;
    private RoundedPasswordField clerkOldPwField, clerkNewPwField;
    private JButton btnUpdate, btnBack, btnViewReservations;
    private boolean hasPreviousMessage;
    private GridBagConstraints gbc = new GridBagConstraints();

    public UserPanel(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        fName = new JLabel("<html><p style='color:white; font-size: 16px'>First Name</p></html>");
        fNameText = new RoundedTextField(20);
        lName = new JLabel("<html><p style='color:white; font-size: 16px'>Last Name</p></html>");
        lNameText = new RoundedTextField(20);
        street = new JLabel("<html><p style='color:white; font-size: 16px'>Address</p></html>");
        streetText = new RoundedTextField(20);
        state = new JLabel("<html><p style='color:white; font-size: 16px'>State</p></html>");
        stateText = new RoundedTextField(20);
        zip = new JLabel("<html><p style='color:white; font-size: 16px'>Zip Code</p></html>");
        zipText = new RoundedTextField(20);
        btnUpdate = new RoundedButton("Update Account");
        btnViewReservations = new RoundedButton("View Reservations");
        btnBack = new RoundedButton("Back");


        fillLayout();

        /**
         * Update Account ActionListener.
         * Requests a User modification.
         * Updates the page with the newly modified information and returns a success message
         * if successful.
         * Returns an error message if unsuccessful.
         */
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();

                Session session = getFrame().getSession();
                String username = session.getUser().getUsername();
                String firstName = getfNameText();
                String lastName = getlNameText();
                String street = getStreetText();
                String state = getStateText();
                String zip = getZipText();
                if(session.getUser().getAccountType().equals(Account.CLERK)){
                    getFrame().getAppController().resetPassword(username, getClerkOldPwField(), getClerkNewPwField());
                }
                User user = getFrame().getAppController().modifyUser(session.getId(), username, firstName, lastName,
                                                                     street, state, zip, true);

                if(user != null) {
                    populateAccount(user);
                    displayMessage("Update was successful!", "green");
                }
                else displayMessage("Update was unsuccessful.", "red");
            }
        });

        /**
         * View Upcoming Reservations ActionListener.
         */
        btnViewReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
            }
        });

        /**
         * Back button ActionListener.
         */
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
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("<html><p style='color:" + color + "'>" + message + "</p></html>"), gbc);
        revalidate();
        repaint();
        hasPreviousMessage = true;
    }

    /**
     * Clears the previous message so that messages do not overwhelm the user interface.
     */
    private void clearMessage(){
        gbc.gridy--;
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    /**
     * Given a User object, populate the JTextFields on the page. Displays password reset fields for Clerk account
     * types.
     * @param user The user to display on the page.
     */
    public void populateAccount(User user){
        if(user.getAccountType() == Account.CLERK){
            clerkOldPwField = new RoundedPasswordField(20);
            clerkNewPwField = new RoundedPasswordField(20);
            clerkOldPwLabel = new JLabel("<html><p style='color:white; font-size:16px'>Clerk Current Password</p></html>");
            clerkNewPwLabel = new JLabel("<html><p style='color:white; font-size:16px'>Clerk New Password</p></html>");

            gbc.insets = new Insets(0, 0, 0, 16);
            gbc.gridx = 0;
            gbc.gridy++;
            add(clerkOldPwLabel, gbc);
            gbc.insets = new Insets(0, 6, 0, 0);
            gbc.gridx = 1;
            add(clerkOldPwField, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
            add(clerkNewPwLabel, gbc);
            gbc.gridx++;
            add(clerkOldPwField, gbc);
        }
        fNameText.setText(user.getFirstName());
        lNameText.setText(user.getLastName());
        streetText.setText(user.getStreet());
        stateText.setText(user.getState());
        zipText.setText(user.getZipCode());
        revalidate();
        repaint();
    }

    /**
     * Provides the initial grid structure of the Register Page.
     */
    private void fillLayout(){
        gbc.gridy = gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(16, 0, 0, 0);
        add(fName, gbc);
        gbc.gridx++;
        add(fNameText, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        add(lName, gbc);
        gbc.gridx++;
        add(lNameText, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        add(street, gbc);
        gbc.gridx++;
        add(streetText, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        add(state, gbc);
        gbc.gridx++;
        add(stateText, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        add(zip, gbc);
        gbc.gridx++;
        add(zipText, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(btnUpdate, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx++;
        add(btnViewReservations, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx++;
        add(btnBack, gbc);
    }

    /**
     * Standard getter and setter methods follow through the end of the page.
     */
    public String getfNameText() {
        return fNameText.getText();
    }

    public void setfNameText(String fNameText) {
        this.fNameText.setText(fNameText);
    }

    public String getlNameText() {
        return lNameText.getText();
    }

    public void setlNameText(String lNameText) {
        this.lNameText.setText(lNameText);
    }

    public String getStreetText() {
        return streetText.getText();
    }

    public void setStreetText(String streetText) {
        this.streetText.setText(streetText);
    }

    public String getStateText() {
        return stateText.getText();
    }

    public void setStateText(String stateText) {
        this.stateText.setText(stateText);
    }

    public String getZipText() {
        return zipText.getText();
    }

    public void setZipText(String zipText) {
        this.zipText.setText(zipText);
    }

    private Frame getFrame(){
        return this.frame;
    }

    public char[] getClerkOldPwField() {
        return clerkOldPwField.getPassword();
    }

    public char[] getClerkNewPwField() {
        return clerkNewPwField.getPassword();
    }
}
