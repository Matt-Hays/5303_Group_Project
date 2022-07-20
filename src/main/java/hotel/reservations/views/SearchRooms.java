package hotel.reservations.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SearchRooms extends JPanel {
    private JTextField arrival;
    private JTextField departure;
    private JButton searchRoomsBtn;
    private JPanel searchRoomsPanel;
    private JTextField guestUsername;
    private JLabel guestUsernameLabel;

    public SearchRooms() {
        this.add(searchRoomsPanel);
        guestUsername.setVisible(false);
        guestUsernameLabel.setVisible(false);
    }

    public String getArrival() {
        return arrival.getText();
    }

    public String getDeparture() {
        return departure.getText();
    }

    public String getGuestUsername(){
        if(guestUsername.isVisible()){
            return guestUsername.getText();
        }
        return null;
    }

    public void toggleClerkInputFieldOn(){
        guestUsername.setVisible(true);
        guestUsernameLabel.setVisible(true);
    }

    public void addSearchRoomsListener(ActionListener listenForSearchRoomsBtn) {
        searchRoomsBtn.addActionListener(listenForSearchRoomsBtn);
    }
}
