package hotel.reservations.views.clerk;

import hotel.reservations.models.room.Room;
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
            btnGenerateBilling;
    private RoundedTextField guestUsernameField;
    private JLabel guestUsernameLabel;
    private GridBagConstraints gbc;

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

        gbc = new GridBagConstraints();

        fillLayout();
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
}
