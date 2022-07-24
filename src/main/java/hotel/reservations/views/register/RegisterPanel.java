package hotel.reservations.views.register;

import hotel.reservations.views.controller.GuiHandler;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedPasswordField;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterPanel extends ThemedPanel {
    private GuiHandler guiHandler;
    private RoundedTextField usernameField, firstNameField, lastNameField, addressField, stateField, zipCodeField;
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
                String state = getStateField();
                String zipCode = getZipCodeField();
                try {
                    getGuiHandler()
                            .getApplicationController()
                            .registerUser(username, password, firstName, lastName, address, state, zipCode);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeScreen("home");
            }
        });
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

    private void setGuiHandler(GuiHandler guiHandler){
        this.guiHandler = guiHandler;
    }

    private GuiHandler getGuiHandler(){
        return guiHandler;
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
