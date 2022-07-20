package hotel.reservations.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ModifyRoom extends JPanel {
    private JPanel modifyRoomPanel;
    private JButton modifyRoomBtn;
    private JTextField roomIdTextField;
    private JTextField bedTypeTextField;
    private JTextField numBedsTextField;
    private JTextField smokingTextField;
    private JTextField nightlyRateTextField;

    public ModifyRoom(){
        this.add(modifyRoomPanel);
    }

    public void prePopulatePage(String roomId, String bedType, String numBeds, String smoking, String nightlyRate){
        roomIdTextField.setText(roomId);
        bedTypeTextField.setText(bedType);
        numBedsTextField.setText(numBeds);
        smokingTextField.setText(smoking);
        nightlyRateTextField.setText(nightlyRate);
    }

    public String getRoomIdTextField() {
        return roomIdTextField.getText();
    }

    public void setRoomIdTextField(JTextField roomIdTextField) {
        this.roomIdTextField = roomIdTextField;
    }

    public String getBedTypeTextField() {
        return bedTypeTextField.getText();
    }

    public void setBedTypeTextField(JTextField bedTypeTextField) {
        this.bedTypeTextField = bedTypeTextField;
    }

    public String getNumBedsTextField() {
        return numBedsTextField.getText();
    }

    public void setNumBedsTextField(JTextField numBedsTextField) {
        this.numBedsTextField = numBedsTextField;
    }

    public String getSmokingTextField() {
        return smokingTextField.getText();
    }

    public void setSmokingTextField(JTextField smokingTextField) {
        this.smokingTextField = smokingTextField;
    }

    public String getNightlyRateTextField() {
        return nightlyRateTextField.getText();
    }

    public void setNightlyRateTextField(JTextField nightlyRateTextField) {
        this.nightlyRateTextField = nightlyRateTextField;
    }

    public void addModifyRoomListener(ActionListener listenForModifyRoomBtn){
        modifyRoomBtn.addActionListener(listenForModifyRoomBtn);
    }
}
