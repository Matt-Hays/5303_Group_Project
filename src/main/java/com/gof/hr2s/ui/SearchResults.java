package com.gof.hr2s.ui;

import javax.swing.*;

public class SearchResults extends JPanel {
    private JPanel searchResults;

    public SearchResults() {
        this.add(searchResults);
    }

    public void createTextField(String newText) {
        JLabel newLabel = new JLabel(newText);
        this.add(newLabel);
    }
}
