package hotel.reservations.views.user;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class UserPanel extends ThemedPanel {
    private final Frame frame;
    private JLabel fName, lName, street, state, zip;
    private JTextField fNameText, lNameText, streetText, stateText, zipText;
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
                User user = getFrame().getAppController().modifyUser(session.getId(), username, firstName, lastName, street, state, zip, true);
                populateAccount(user);
                displayMessage("Update was successful!");
                hasPreviousMessage = true;
            }
        });

        btnViewReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
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

    private void displayMessage(String message){
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("<html><p style='color:green'>" + message + "</p></html>"), gbc);
        revalidate();
        repaint();
    }

    private void clearMessage(){
        gbc.gridy--;
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    public void populateAccount(User user){
        fNameText.setText(user.getFirstName());
        lNameText.setText(user.getLastName());
        streetText.setText(user.getStreet());
        stateText.setText(user.getState());
        zipText.setText(user.getZipCode());
        revalidate();
        repaint();
    }

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
}
