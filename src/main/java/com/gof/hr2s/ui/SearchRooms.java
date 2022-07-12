package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SearchRooms extends JPanel {
    private JTextField arrival;
    private JTextField departure;
    private JButton searchRoomsBtn;
    private JPanel searchRoomsPanel;

    public SearchRooms() {
        this.add(searchRoomsPanel);
    }

    public String getArrival() {
        return arrival.getText();
    }

    public String getDeparture() {
        return departure.getText();
    }

    public void addSearchRoomsListener(ActionListener listenForSearchRoomsBtn) {
        searchRoomsBtn.addActionListener(listenForSearchRoomsBtn);
    }
}
