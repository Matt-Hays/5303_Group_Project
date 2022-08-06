package hotel.reservations.views.reports;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Room;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatusReportPanel extends ThemedPanel {
    private Frame frame;
    private JLabel pageTitle, roomNumTableHeader, roomStatusTableHeader, roomNumber, roomStatus;
    private RoundedButton btnBack;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private List<Room> roomCache;

    public StatusReportPanel(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><p style='color:white; font-size:24px; font-weight:bold'>Status Report</p></html>");
        roomNumTableHeader = new JLabel("<html><p style='color:white; font-size:16px'>Room Number</p></html>");
        roomStatusTableHeader = new JLabel("<html><p style='color:white; font-size:16px'>Room Occupied</p></html>");
    }

    private void initDisplay(){
        btnBack = new RoundedButton("Back");

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("account");
            }
        });

        add(btnBack);

        revalidate();
        repaint();
    }

    public void fillLayout(List<Room> rooms){
        clearLayout();
        if(rooms.isEmpty()) {
            this.roomCache = null;
            initDisplay();
            return;
        }
        this.roomCache = rooms;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(getWidth(),  getHeight()));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setAutoscrolls(true);

        scrollPanel = new ThemedPanel();
        scrollPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        scrollPanel.add(pageTitle, gbc);
        gbc.gridy++;
        scrollPanel.add(roomNumTableHeader, gbc);
        gbc.gridx++;
        scrollPanel.add(roomStatusTableHeader, gbc);
        gbc.gridx--;
        gbc.gridy++;

        for(Room room : rooms){
            insertRoom(room, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.insets = new Insets(8, 0, 8, 0);
        }
        btnBack = new RoundedButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("clerk");
            }
        });
        scrollPanel.add(btnBack);
        scrollPane.setViewportView(scrollPanel);

        scrollPane.revalidate();
        scrollPane.repaint();
        add(scrollPane);
        revalidate();
        repaint();
    }

    public void clearLayout(){
        if(getComponents().length > 0){
            for(Component comp : getComponents()){
                remove(comp);
            }
        }
    }

    private void insertRoom(Room room, GridBagConstraints gbc){
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getRoomId() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + room.getOccupied() + "</p></html>"), gbc);
    }

    private Frame getFrame(){
        return this.frame;
    }
}
