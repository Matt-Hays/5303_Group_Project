/**
 * @file ClerkPanel.java
 * @auhor Matthew Hays
 * @brief The custom page *JPanel* that is provided to allow the Clerk
 *        to complete their workflows.
 * @dependencies Frame.java
 */

package hotel.reservations.views.clerk;

import hotel.reservations.models.room.Room;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.user.User;
import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

public class ClerkPanel extends ThemedPanel {
    private final Frame frame;
    private RoundedButton btnRoomMgmt, btnViewStatusReport, btnCheckInOut, btnMakeReservation, btnModifyReservation,
            btnGenerateBilling, btnBack;
    private RoundedTextField guestUsernameField;
    private JLabel guestUsernameLabel;
    private GridBagConstraints gbc;
    private boolean hasPreviousMessage = false;

    /**
     * Attach the Frame dependency and layout the page components.
     * @param frame Frame.java interface.
     */
    public ClerkPanel(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());

        guestUsernameLabel = new JLabel("<html><p style='color:white; font-size:16px'>Guest's Username</p></html>");
        guestUsernameField = new RoundedTextField(20);

        btnRoomMgmt = new RoundedButton("Room Management");
        btnViewStatusReport = new RoundedButton("View Status Report");
        btnCheckInOut = new RoundedButton("Check-In/Out");
        btnMakeReservation = new RoundedButton("Make Reservation");
        btnModifyReservation = new RoundedButton("Modify Reservation");
        btnGenerateBilling = new RoundedButton("Generate Billing Report");
        btnBack = new RoundedButton("Back");

        btnRoomMgmt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("roomMgmt");
                System.out.println("Room Management Button Clicked");
            }
        });

        btnViewStatusReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Room> rooms = getFrame().getAppController().getRooms();
                getFrame().getStatusReportPanel().fillLayout(rooms);
                getFrame().changeScreen("status-report");
            }
        });

        btnCheckInOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Reservation> reservations = getFrame().getAppController().getReservationByUsername(getUsernameField());
                getFrame().getReservationsPanel().fillLayout(reservations);
                getFrame().getReservationsPanel().clerkMode();
                getFrame().changeScreen("reservations");
            }
        });

        btnModifyReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                String username = guestUsernameField.getText();

                List<Reservation> reservations = getFrame().getAppController().getReservationByUsername(username);
                getFrame().getReservationsPanel().fillLayout(reservations);
                getFrame().getReservationsPanel().clerkMode();
                getFrame().changeScreen("reservations");
            }
        });

        btnGenerateBilling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //view logic
                List<Invoice> invoices = getFrame().getAppController().generateBillingReport(getUsernameField());
                getFrame().getBillingReportPanel().fillLayout(invoices);
                getFrame().changeScreen("billing-report");
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("home");
            }
        });


        gbc = new GridBagConstraints();

        fillLayout();
    }

    public String getUsernameField() {
        return guestUsernameField.getText();
    }

    /**
     * Layout panel components.
     */
    private void fillLayout(){
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridwidth = 1;
        add(guestUsernameLabel, gbc);
        gbc.gridx++;
        add(guestUsernameField, gbc);
        gbc.gridx--;

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        insertComponent(btnRoomMgmt);
        insertComponent(btnViewStatusReport);
        insertComponent(btnCheckInOut);
        insertComponent(btnMakeReservation);
        insertComponent(btnModifyReservation);
        insertComponent(btnGenerateBilling);
        insertComponent(btnBack);

        revalidate();
        repaint();
    }

    /**
     * Encapsulate a repeated function - add a new component to the panel.
     * @param comp The component to add.
     */
    private void insertComponent(Component comp){
        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 4, 0);
        add(comp, gbc);
    }

    private Frame getFrame() {
        return frame;
    }

    /**
     * Given a message to display and the desired (standard named html) color, display the message to the user.
     * @param message The message to be displayed.
     * @param color The standard HTML named color of the text.
     */
    public void displayMessage(String message, String color){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(64, 0, 0, 0);
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
