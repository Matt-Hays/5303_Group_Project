package hotel.reservations.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton viewAccountBtn;
    private JButton searchRoomsBtn;
    private JButton logout;
    private JPanel controlPanel;
    private JButton updateAccountBtn;
    private JButton modifyRoomsBtn;
    private JButton createClerkBtn;
    private JButton modifyReservationBtn;

    public ControlPanel(){
        this.add(controlPanel);
        modifyRoomsBtn.setVisible(false);
        createClerkBtn.setVisible(false);
    }

    public void toggleModifyRoomsOn(){
        modifyRoomsBtn.setVisible(true);
    }

    public void toggleCreateClerkOn(){
        createClerkBtn.setVisible(true);
    }


    public void toggleModifyRoomsOff(){
        modifyRoomsBtn.setVisible(false);
    }

    public void toggleCreateClerkOff(){
        createClerkBtn.setVisible(false);
    }

    public void addViewAccountListener(ActionListener listenForViewAccountBtn){
        viewAccountBtn.addActionListener(listenForViewAccountBtn);
    }

    public void addSearchRoomsListener(ActionListener listenForSearchRoomsBtn){
        searchRoomsBtn.addActionListener(listenForSearchRoomsBtn);
    }

    public void addUpdateAccountListener(ActionListener listenForUpdateAccount){
        updateAccountBtn.addActionListener(listenForUpdateAccount);
    }

    public void addCreateClerkListener(ActionListener listenForCreateClerkBtn){
        createClerkBtn.addActionListener(listenForCreateClerkBtn);
    }

    public void modifyRoomsListener(ActionListener listenForModifyRoomsBtn){
        modifyRoomsBtn.addActionListener(listenForModifyRoomsBtn);
    }

    public void addModifyReservationsListener(ActionListener listenForModifyReservationBtn){
        modifyReservationBtn.addActionListener(listenForModifyReservationBtn);
    }

    public void addLogoutBtnListener(ActionListener listenForLogoutBtn){
        logout.addActionListener(listenForLogoutBtn);
    }
}
