package hotel.reservations.views.room;

import hotel.reservations.models.room.Room;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class RoomsPanel extends ThemedPanel {
    private final Frame frame;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private RoundedButton btnBack;
    private List<Room> roomCache;
    private LocalDate arrival, departure;

    public RoomsPanel(Frame frame) {
        this.frame = frame;
    }

    public void fillLayout(List<Room> rooms, LocalDate arrival, LocalDate departure){
        if(roomCache != null) clearPanel();
        this.roomCache = rooms;
        this.arrival = arrival;
        this.departure = departure;
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(getWidth(),  getHeight()));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setAutoscrolls(true);
        scrollPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

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
                getFrame().changeScreen("search");
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

    private void insertRoom(Room room, GridBagConstraints gbc){
        System.out.println(room.getRoomId());
        scrollPanel.add(new JLabel("<html><p style='color:black; font-size:16px; font-weight:bold'>" + room.getRoomId() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:black; font-size:16px; font-weight:bold'>" + room.getBedType() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:black; font-size:16px; font-weight:bold'>" + room.getNumBeds() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:black; font-size:16px; font-weight:bold'>" + room.getSmoking() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:black; font-size:16px; font-weight:bold'>" + room.getNightlyRate() + "</p></html>"), gbc);
        gbc.gridy++;
        gbc.gridx = 2;
        RoundedButton tempBtn = new RoundedButton("View");
        tempBtn.setActionCommand(String.valueOf(room.getRoomId()));
        tempBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomId = e.getActionCommand();
                // Get the Room from the room id
                for(Room room : roomCache){
                    if(String.valueOf(room.getRoomId()).equals(roomId)){
                        getFrame().getRoomPanel().setRoom(room, arrival, departure);
                        break;
                    }
                }
                // Pass the room to the Room Panel
                // Change the View to the Room Panel
                getFrame().changeScreen("room");
            }
        });
        scrollPanel.add(tempBtn, gbc);
    }

    private void clearPanel(){
        for(Component comp : getComponents())
            remove(comp);
    }

    public Frame getFrame() {
        return frame;
    }

    public List<Room> getRoomCache() {
        return roomCache;
    }
}
