/**
 * @file HomePanel.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that provides a display for a single
 *        Reservation domain object.
 * @dependencies Frame.java
 */

package hotel.reservations.views.reservation;

import com.github.lgooddatepicker.components.DatePicker;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.user.Account;
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

public class ReservationPanel extends ThemedPanel {
    private final Frame frame;
    private JLabel reservationIdLabel, customerIdLabel, roomNumberLabel, arrivalLabel, departureLabel, statusLabel;
    private DatePicker arrivalField, departureField;
    private RoundedTextField roomNumberField, statusField;
    private RoundedButton btnBack, btnUpdate, btnCancel, btnCheckIn, btnCheckOut;
    private Reservation reservationCache;
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Attach the Frame.java dependency and set the panel's layout
     * @param frame
     */
    public ReservationPanel(Frame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
    }

    /**
     * Given a Reservation domain object, layout the panel using the object.
     * @param reservation Reservation.java domain object.
     */
    public void fillLayout(Reservation reservation){
        this.reservationCache = reservation;

        UUID resId = reservation.getReservationId();
        UUID cusId = reservation.getCustomerId();
        int roomNum = reservation.getRoomNumber();
        LocalDate arr = reservation.getArrival();
        LocalDate dep = reservation.getDeparture();

        reservationIdLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + resId + "</p></html>");
        customerIdLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + cusId + "</p></html>");
        roomNumberLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + roomNum + "</p></html>");
        arrivalLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + arr + "</p></html>");
        departureLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + dep + "</p></html>");
        statusLabel = new JLabel("<html><p style='color:white; font-size:16px'>" + dep + "</p></html>");

        roomNumberField = new RoundedTextField(20);
        roomNumberField.setText(String.valueOf(roomNum));

        arrivalField = new DatePicker();
        arrivalField.setDate(arr);

        departureField = new DatePicker();
        departureField.setDate(dep);

        btnUpdate = new RoundedButton("Update Reservation");
        btnCancel = new RoundedButton("Cancel Reservation");
        btnBack = new RoundedButton("Back");
        btnCheckIn = new RoundedButton("Check-In");
        btnCheckOut = new RoundedButton("Check-Out");

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update Reservation
                Reservation updatedRes = new Reservation(reservationCache.getReservationId(), reservationCache.getCustomerId(),
                        reservationCache.getInvoiceId(), getRoomNumberField(), reservationCache.getCreatedAt(), getArrivalField(),
                        getDepartureField(), reservationCache.getStatus());
                // Cancel Reservation
                getFrame().getAppController().modifyReservation(updatedRes);
                getFrame().getHomePanel().displayMessage("Reservation updated!", "green");
                getFrame().changeScreen("home");
                clearLayout();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getAppController().cancelReservation(reservationCache);
                getFrame().getHomePanel().displayMessage("Reservation successfully cancelled!", "green");
                getFrame().changeScreen("home");
                clearLayout();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("reservations");
                clearLayout();
            }
        });

        btnCheckIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getAppController().checkIn(reservation);
                getFrame().getHomePanel().displayMessage("Guest Checked-In", "green");
                getFrame().changeScreen("home");
                clearLayout();
            }
        });

        btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getAppController().checkOut(reservation);
                getFrame().getHomePanel().displayMessage("Guest Checked-Out", "yellow");
                getFrame().changeScreen("home");
                clearLayout();
            }
        });


        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        add(reservationIdLabel, gbc);
        gbc.gridy++;
        add(customerIdLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        add(roomNumberLabel, gbc);
        gbc.gridx++;
        add(roomNumberField, gbc);
        gbc.gridx--;

        gbc.gridy++;
        add(arrivalLabel, gbc);
        gbc.gridx++;
        add(arrivalField, gbc);
        gbc.gridx--;

        gbc.gridy++;
        add(departureLabel, gbc);
        gbc.gridx++;
        add(departureField, gbc);
        gbc.gridx--;

        gbc.gridy++;
        add(btnUpdate, gbc);
        gbc.gridx++;
        add(btnCancel, gbc);
        gbc.gridx++;
        add(btnBack, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        if(reservation.getStatus().equals(ReservationStatus.AWAITING) && getFrame().getSession().getUser().getAccountType().equals(Account.CLERK)){
            add(btnCheckIn, gbc);
        }
        else if(reservation.getStatus().equals(ReservationStatus.CHECKEDIN) && getFrame().getSession().getUser().getAccountType().equals(Account.CLERK)){
            add(btnCheckOut, gbc);
        }

        revalidate();
        repaint();
    }

    /**
     * Standard getters and setters from here through the end of the file.
     */
    private Frame getFrame() {
        return frame;
    }

    public int getRoomNumberField() {
        return Integer.parseInt(roomNumberField.getText());
    }

    public LocalDate getArrivalField() {
        return arrivalField.getDate();
    }

    public LocalDate getDepartureField() {
        return departureField.getDate();
    }

    public ReservationStatus getStatusField() {
        switch(statusField.getText()){
            case "AWAITING":
                return ReservationStatus.AWAITING;
            case "CHECKEDIN":
                return ReservationStatus.CHECKEDIN;
            case "CANCELLED":
                return ReservationStatus.CANCELLED;
            case "COMPLETE":
                return ReservationStatus.COMPLETE;
            default:
                return null;
        }
    }

    private void clearLayout(){
        this.reservationCache = null;
        for(Component comp : getComponents())
            remove(comp);
    }
}
