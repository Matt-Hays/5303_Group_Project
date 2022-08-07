/**
 * @file SearchPanel.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that provides access to the
 *        Search Rooms workflow.
 * @dependencies Frame.java
 */

package hotel.reservations.views.search;

import com.github.lgooddatepicker.components.DatePicker;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class SearchPanel extends ThemedPanel {
    private Frame frame;
    private DatePicker arrivalDatePicker, departureDatePicker;
    private JLabel pageHeader, arrivalLabel, departureLabel, smokingLabel, numBedsLabel, bedTypeLabel, occupiedLabel, rateLabel;
    private RoundedTextField smokingField, numBedsField, bedTypeField, occupiedField, rateField;
    private RoundedButton btnSearch, btnBack;

    /**
     * Attach the Frame.java dependency and generate the page layout.
     * @param frame Frame.java interface.
     */
    public SearchPanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());

        pageHeader = new JLabel("<html><h1 style='font-size:28; color:white'>Search for a Room</h1></html>");

        arrivalLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Arrival: </h1></html>");
        arrivalDatePicker = new DatePicker();

        departureLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Departure: </h1></html>");
        departureDatePicker = new DatePicker();

        smokingLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Smoking Preference</h1></html>");
        smokingField = new RoundedTextField(20);

        numBedsLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Number of Beds</h1></html>");
        numBedsField = new RoundedTextField(20);

        bedTypeLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Bed Size</h1></html>");
        bedTypeField = new RoundedTextField(20);

        rateLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Maximum Nightly Rate</h1></html>");
        rateField = new RoundedTextField(20);


        btnSearch = new RoundedButton("Search");
        btnBack = new RoundedButton("Back");

        fillLayout();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("home");
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate arrival = getArrivalDatePicker();
                LocalDate departure = getDepartureDatePicker();
                int numBeds = getNumBedsField();
                Bed bedType = getBedTypeField();
                boolean smoking = getSmokingField();

                List<Room> rooms = getFrame().getAppController().searchRooms(arrival, departure, numBeds, bedType, smoking);

                getFrame().getRoomsPanel().fillLayout(rooms, arrival, departure);
                getFrame().changeScreen("rooms");
            }
        });
    }

    private void setFrame(Frame frame) {
        this.frame = frame;
    }

    private Frame getFrame(){
        return frame;
    }

    /**
     * Populate the layout with panel components.
     */
    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(pageHeader, gbc);

        // Smoking
        gbc.gridy++;
        add(smokingLabel, gbc);
        gbc.gridx++;
        add(smokingField, gbc);
        gbc.gridx--;

        // Number of Beds
        gbc.gridy++;
        add(numBedsLabel, gbc);
        gbc.gridx++;
        add(numBedsField, gbc);
        gbc.gridx--;

        // Bed Type
        gbc.gridy++;
        add(bedTypeLabel, gbc);
        gbc.gridx++;
        add(bedTypeField, gbc);
        gbc.gridx--;

        // Arrival
        gbc.gridy++;
        add(arrivalLabel, gbc);
        gbc.gridy++;
        add(arrivalDatePicker, gbc);

        // Departure
        gbc.gridx++;
        gbc.gridy--;
        add(departureLabel, gbc);
        gbc.gridy++;
        add(departureDatePicker, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(8, 0, 8, 0);

        gbc.gridy++;
        add(btnSearch, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx++;
        add(btnBack, gbc);
    }

    /**
     * Standard getter and setter methods from here through the end of the file.
     */
    public LocalDate getArrivalDatePicker() {
        return arrivalDatePicker.getDate();
    }

    public LocalDate getDepartureDatePicker() {
        return departureDatePicker.getDate();
    }

    public boolean getSmokingField() {
        return smokingField.getText().equalsIgnoreCase("true");
    }

    public int getNumBedsField() {
        return Integer.valueOf(numBedsField.getText());
    }

    public Bed getBedTypeField() {
        switch(bedTypeField.getText().toLowerCase()){
            case "twin":
                return Bed.TWIN;
            case "full":
                return Bed.FULL;
            case "queen":
                return Bed.QUEEN;
            case "king":
                return Bed.KING;
            default:
                return null;
        }
    }

    public String getRateField() {
        return rateField.getText();
    }
}
