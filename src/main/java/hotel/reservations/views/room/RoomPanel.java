package hotel.reservations.views.room;

import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.User;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class RoomPanel extends ThemedPanel {
    private final Frame frame;

    private JLabel roomId, smoking, numBeds, bedType, occupied, nightlyRate;
    private RoundedButton btnMakeReservation, btnBack;
    private Room roomCache;
    private LocalDate arrival, departure;

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(roomId, gbc);
        gbc.gridy++;
        add(smoking, gbc);
        gbc.gridy++;
        add(numBeds, gbc);
        gbc.gridy++;
        add(bedType, gbc);
        gbc.gridy++;
        add(occupied, gbc);
        gbc.gridy++;
        add(nightlyRate, gbc);
        gbc.gridy++;
        add(btnMakeReservation, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnBack, gbc);

        btnMakeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User sessionUser = getFrame().getSession().getUser();
                getFrame().getAppController().createReservation(sessionUser, roomCache, arrival, departure);
                getFrame().getHomePanel().displayMessage("Reservation Successfully created!", "green");
                getFrame().changeScreen("home");
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("rooms");
            }
        });
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
}
