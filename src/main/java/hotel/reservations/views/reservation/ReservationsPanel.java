/**
 * @file ReservationsPanel.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that provides a display for a list of
 *        Reservation domain objects.
 * @dependencies Frame.java
 */

package hotel.reservations.views.reservation;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservationsPanel extends ThemedPanel {
    private final Frame frame;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private RoundedButton btnBack;
    private List<Reservation> reservationsCache;

    /**
     * Attach the Frame.java dependency and initiate the panel with a "Back" button.
     * @param frame Frame.java interface.
     */
    public ReservationsPanel(Frame frame) {
        this.frame = frame;

        initDisplay();
    }

    /**
     * Initiate the panel with a single "Back" button.
     */
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

    /**
     * Given a list of Reservation domain objects, layout the panle using the list.
     * @param reservations List of Reservation.java domain objects.
     */
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

    /**
     * Clear the panel of all components to remove the previous state of the panel.
     */
    public void clearLayout(){
        if(getComponents().length > 0){
            for(Component comp : getComponents()){
                remove(comp);
            }
        }
    }

    /**
     * Given a single reservation and the current GridBagConstraints layout object,
     * add the reservation to the ScrollPanel.
     * @param res Reservation.java domain object.
     * @param gbc GridBagConstraints layout object.
     */
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

    private Frame getFrame() {
        return frame;
    }
}
