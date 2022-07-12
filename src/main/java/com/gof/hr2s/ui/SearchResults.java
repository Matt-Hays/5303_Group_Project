package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SearchResults extends JPanel {
    private JPanel searchResults;

    public SearchResults() {
        this.add(searchResults);
    }

    public void createTextField(String newText) {
        JLabel newLabel = new JLabel(newText);
        this.add(newLabel);
    }

    public JButton createButton(String btnLabel, String roomId){
        JButton btn = new JButton(btnLabel);
        btn.setActionCommand(roomId);
        this.add(btn);
        return btn;
    }

    public void addNewBtnEventListener(ActionListener listenForRoomBtn, JButton btn){
        btn.addActionListener(listenForRoomBtn);
    }
}
