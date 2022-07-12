package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UpdateReservation extends JPanel {
    private JTextField arrivalTextField;
    private JTextField departureTextField;
    private JLabel reservationIdLabel;
    private JPanel updateReservation;
    private JButton updateReservationBtn;

    public UpdateReservation(){
        this.add(updateReservation);
    }

    public String getArrivalTextField() {
        return arrivalTextField.getText();
    }

    public String getDepartureTextField() {
        return departureTextField.getText();
    }

    public void setDepartureField(String newText){
        departureTextField.setText(newText);
    }

    public void setArrivalField(String newText){
        arrivalTextField.setText(newText);
    }

    public void setModifyReservationBtnCmds(String reservationId){
        updateReservationBtn.setActionCommand(reservationId);
    }


    public void setReservationIdLabel(String reservationIdLabel) {
        this.reservationIdLabel.setText(reservationIdLabel);
    }

    public void addModifyReservationListener(ActionListener listenForModifyReservation){
        updateReservationBtn.addActionListener(listenForModifyReservation);
    }
}
