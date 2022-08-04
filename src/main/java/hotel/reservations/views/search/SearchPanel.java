package hotel.reservations.views.search;

import com.github.lgooddatepicker.components.DatePicker;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel extends ThemedPanel {
    private Frame frame;
    private DatePicker datePicker1, datePicker2;
    private JLabel pageHeader, arrivalLabel, departureLabel;
    private RoundedButton btnSearch, btnBack;

    public SearchPanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());

        datePicker1 = new DatePicker();
        datePicker2 = new DatePicker();
        pageHeader = new JLabel("<html><h1 style='font-size:28; color:white'>Search for a Room</h1></html>");
        arrivalLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Arrival: </h1></html>");
        departureLabel = new JLabel("<html><h1 style='font-size:14; color:white'>Departure: </h1></html>");
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
                // SEARCH ROOMS !!! IMPLEMENT !!!
                getFrame().getAppController();
            }
        });
    }

    private void setFrame(Frame frame) {
        this.frame = frame;
    }

    private Frame getFrame(){
        return frame;
    }

    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0,24, 0);
        gbc.gridwidth = 2;
        add(pageHeader, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(arrivalLabel, gbc);
        gbc.gridx++;
        add(datePicker1, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(departureLabel, gbc);
        gbc.gridx++;
        add(datePicker2, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(24, 128, 0, 0);
        add(btnSearch, gbc);
        gbc.insets = new Insets(24, 0, 0, 0);
        gbc.gridx++;
        add(btnBack, gbc);
    }
}
