package hotel.reservations.views.reservation;

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
import java.time.LocalDate;
import java.util.List;

public class ReservationsPanel extends ThemedPanel {
    private final Frame frame;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private RoundedButton btnBack;
    private List<Reservation> reservationsCache;

    public ReservationsPanel(Frame frame) {
        this.frame = frame;

        initDisplay();
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

    public void fillLayout(List<Reservation> reservations){
        clearLayout();
        if(reservations.isEmpty()) {
            this.reservationsCache = null;
            initDisplay();
            return;
        }
        this.reservationsCache = reservations;

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

        for(Reservation res : reservations){
            if(res.getStatus().equals(ReservationStatus.CANCELLED) || res.getStatus().equals(ReservationStatus.COMPLETE))
                continue;
            insertReservation(res, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.insets = new Insets(8, 0, 8, 0);
        }
        btnBack = new RoundedButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("account");
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

    private void insertReservation(Reservation res, GridBagConstraints gbc){
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getReservationId() + "</p></html>"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getCustomerId() + "</p></html>"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getRoomNumber() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getArrival() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getDeparture() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + res.getStatus() + "</p></html>"), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        RoundedButton tempBtn = new RoundedButton("View");
        tempBtn.setActionCommand(String.valueOf(res.getReservationId()));
        tempBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resId = e.getActionCommand();
                System.out.println(resId);
                 // Get the Reservation from the room id
                for(Reservation res : reservationsCache){
                    if(String.valueOf(res.getReservationId()).equals(resId)){
                        getFrame().getReservationPanel().fillLayout(res);
                        break;
                    }
                }
                // Pass the room to the Room Panel
                // Change the View to the Room Panel
                getFrame().changeScreen("reservation");
            }
        });
        scrollPanel.add(tempBtn, gbc);
    }

    public Frame getFrame() {
        return frame;
    }
}
