package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton viewAccountBtn;
    private JButton searchRoomsBtn;
    private JButton logout;
    private JPanel controlPanel;

    public ControlPanel(){
        this.add(controlPanel);
    }

    public void addViewAccountListener(ActionListener listenForViewAccountBtn){
        viewAccountBtn.addActionListener(listenForViewAccountBtn);
    }

    public void addSearchRoomsListener(ActionListener listenForSearchRoomsBtn){
        searchRoomsBtn.addActionListener(listenForSearchRoomsBtn);
    }
}
