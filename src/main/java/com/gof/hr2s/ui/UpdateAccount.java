package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UpdateAccount extends JPanel {
    private JTextField firstName;
    private JTextField lastName;
    private JButton updateAccountBtn;
    private JPanel updateUserPanel;
    private JPasswordField password;

    public UpdateAccount(){
        this.add(updateUserPanel);
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public String getLastName() {
        return lastName.getText();
    }

    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    public char[] getPassword(){
        return password.getPassword();
    }

    public void addModifyAccountListener(ActionListener listenForUpdateAccountBtn){
        updateAccountBtn.addActionListener(listenForUpdateAccountBtn);
    }
}
