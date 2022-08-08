/**
 * @file RoomPanel.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that provides a display for a single
 *        Room domain object. This page is reused for both Guest and Clerk workflows.
 * @dependencies Frame.java
 */

package hotel.reservations.views.room;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

public class RoomPanel extends ThemedPanel {
    private final Frame frame;

    private JLabel roomId, smoking, numBeds, bedType, occupied, nightlyRate;
    private RoundedButton btnMakeReservation, btnBack;
    private Room roomCache;
    private LocalDate arrival, departure;
    private JLabel guestUsernameLabel;
    private RoundedTextField guestUsernameField;

    private boolean hasPreviousMessage = false;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean clerkMode = false;

    /**
     * Attach Frame.java dependency and attach empty JLabel components
     * to the panel.
     * @param frame Frame.java interface.
     */
    public RoomPanel(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());



        roomId = new JLabel();
        smoking = new JLabel();
        numBeds = new JLabel();
        bedType = new JLabel();
        occupied = new JLabel();
        nightlyRate = new JLabel();

        btnMakeReservation = new RoundedButton("Make Reservation");
        btnBack = new RoundedButton("Back");

        gbc.gridy = gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(roomId, gbc);

        layoutComponent(smoking, gbc);

        layoutComponent(numBeds, gbc);

        layoutComponent(bedType, gbc);

        layoutComponent(occupied, gbc);

        layoutComponent(nightlyRate, gbc);

        gbc.gridy++;
        add(btnMakeReservation, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnBack, gbc);

        btnMakeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();

                User sessionUser;
                if(getFrame().getSession() != null){
                    sessionUser = getFrame().getSession().getUser();

                    Reservation res;
                    switch(sessionUser.getAccountType()){
                        case CLERK:
                            String username = guestUsernameField.getText();
                            if (username.isEmpty()) return;
                            res = getFrame().getAppController().clerkCreateReservation(username, roomCache, arrival, departure);
                            break;
                        default:
                            res = getFrame().getAppController().createReservation(sessionUser, roomCache, arrival, departure);
                            break;
                    }

                    if (res != null) {
                        clearClerkMode();
                        getFrame().getHomePanel().displayMessage("Reservation Successfully created!", "green");
                        getFrame().changeScreen("home");
                    } else {
                        displayMessage("Failed to make reservation!", "red");
                    }
                }
                else {
                    getFrame().getLoginPanel().displayMessage("You must be logged in to reserve a room.", "red");
                    getFrame().changeScreen("login");
                }

            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("rooms");
            }
        });
    }

    /**
     * Provides display modification dependent on authentication status. Additionally, provides functionality for
     * differing use cases dependent on user type (Guest, Clerk, or Admin). Updates and reuses JButton components
     * by clearing existing ActionListeners and adding new ActionListeners specific to the needs of the user type.
     */
    public void loggedInDisplay(){
        if(getFrame().getSession().getUser().getAccountType().equals(Account.CLERK)) {
            clerkMode = true;
            guestUsernameLabel = new JLabel("<html><p style='color:white; font-size:16px'>Guest's Username: </p></html>");
            guestUsernameField = new RoundedTextField(20);

            gbc.gridy++;
            gbc.gridx--;
            add(guestUsernameLabel,gbc);
            gbc.gridx++;
            add(guestUsernameField, gbc);
        }

        revalidate();
        repaint();
    }

    private void clearClerkMode(){
        if (clerkMode) {
            remove(guestUsernameLabel);
            remove(guestUsernameField);
            revalidate();
            repaint();
        }
        clerkMode = false;
    }

    public void setRoom(Room room, LocalDate arrival, LocalDate departure){
        this.roomCache = room;
        this.arrival = arrival;
        this.departure = departure;
        roomId.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getRoomId() + "</p></html>");
        smoking.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getSmoking() + "</p></html>");
        numBeds.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getNumBeds() + "</p></html>");
        bedType.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getBedType() + "</p></html>");
        occupied.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getOccupied() + "</p></html>");
        nightlyRate.setText("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getNightlyRate() + "</p></html>");
    }

    private void layoutComponent(Component comp, GridBagConstraints gbc){
        gbc.gridy++;
        add(comp, gbc);
    }

    private Frame getFrame() {
        return frame;
    }

    private String getRoomId() {
        return roomId.getText();
    }

    private String getSmoking() {
        return smoking.getText();
    }

    private String getNumBeds() {
        return numBeds.getText();
    }

    private String getBedType() {
        return bedType.getText();
    }

    private String getOccupied() {
        return occupied.getText();
    }

    private String getNightlyRate() {
        return nightlyRate.getText();
    }

    /**
     * Given a message to display and the desired (standard named html) color, display the message to the user.
     * @param message The message to be displayed.
     * @param color The standard HTML named color of the text.
     */
    public void displayMessage(String message, String color){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("<html><p style='color:" + color + "'>" + message + "</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    /**
     * Removes the last message from the panel to ensure additional messages are not displayed on top of each other.
     */
    private void clearMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }
}
