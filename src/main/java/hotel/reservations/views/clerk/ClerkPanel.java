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

public class ClerkPanel extends ThemedPanel {
    private final Frame frame;
    private RoundedButton btnRoomMgmt, btnViewStatusReport, btnCheckInOut, btnMakeReservation, btnModifyReservation,
            btnGenerateBilling;
    private RoundedTextField guestUsernameField;
    private JLabel guestUsernameLabel;
    private GridBagConstraints gbc;
    private boolean hasPreviousMessage = false;

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

        btnRoomMgmt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Room Management Button");
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

        btnModifyReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                String username = guestUsernameField.getText();
                User guestUser = getFrame().getAppController().getUser(username);
                if (null == guestUser) {
                    System.out.println("invalid username");
                    displayMessage("Invalid username!", "red");
                    return;
                }
                List<Reservation> reservations = getFrame().getAppController().getReservationByUserId(guestUser.getUserId());
                getFrame().getReservationsPanel().fillLayout(reservations);
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


        gbc = new GridBagConstraints();

        fillLayout();
    }

    public String getUsernameField() {
        return guestUsernameField.getText();
    }

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

        revalidate();
        repaint();
    }

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
