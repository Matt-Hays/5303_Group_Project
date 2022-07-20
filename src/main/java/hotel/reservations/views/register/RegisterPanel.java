package hotel.reservations.views.register;

import hotel.reservations.views.GuiHandler;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedPasswordField;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends ThemedJPanel {
    private GuiHandler guiHandler;
    private RoundedTextField usernameField, firstNameField, lastNameField, addressField, cityField, stateField, zipCodeField;
    private RoundedPasswordField passwordField;
    private RoundedButton btnBack, btnRegister;


    public RegisterPanel(GuiHandler guiHandler){
        setGuiHandler(guiHandler);

        setLayout(new GridBagLayout());

        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);
        firstNameField = new RoundedTextField(20);
        lastNameField = new RoundedTextField(20);
        addressField = new RoundedTextField(20);
        cityField = new RoundedTextField(20);
        stateField = new RoundedTextField(2);
        zipCodeField = new RoundedTextField(5);
        btnBack = new RoundedButton("Back");
        btnRegister = new RoundedButton("Register");

        fillLayout();

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = getUsernameField();
                char[] password = getPasswordField();
                String firstName = getFirstNameField();
                String lastName = getLastNameField();
                String address = getAddressField();
                String city = getCityField();
                String state = getStateField();
                String zipCode = getZipCodeField();
                getGuiHandler()
                        .getReservationHandler()
                        .registerUser(username, password, firstName, lastName, address, city, state, zipCode);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeView("home");
            }
        });
    }

    private void setGuiHandler(GuiHandler guiHandler){
        this.guiHandler = guiHandler;
    }

    private GuiHandler getGuiHandler(){
        return guiHandler;
    }

    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
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
        add(cityField, gbc);
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


    public String getUsernameField() {
        return usernameField.getText();
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getAddressField() {
        return addressField.getText();
    }

    public String getCityField() {
        return cityField.getText();
    }

    public String getStateField() {
        return stateField.getText();
    }

    public String getZipCodeField() {
        return zipCodeField.getText();
    }
}
