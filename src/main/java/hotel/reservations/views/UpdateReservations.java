package hotel.reservations.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UpdateReservations extends JPanel {
    private JPanel updateReservations;

    public UpdateReservations(){
        this.add(updateReservations);
    }

    public void createNewLabel(String newLabel){
        JLabel label = new JLabel(newLabel);
        this.add(label);
    }

    public JButton createNewButton(String btnLabel, String btnCommands){
        JButton btn = new JButton(btnLabel);
        btn.setActionCommand(btnCommands);
        this.add(btn);
        return btn;
    }

    public void addBtnEventListeners(ActionListener listenForUpdateReservations, JButton btn){
        btn.addActionListener(listenForUpdateReservations);
    }
}
