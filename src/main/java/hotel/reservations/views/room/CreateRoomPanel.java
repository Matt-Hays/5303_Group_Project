package hotel.reservations.views.room;

import hotel.reservations.models.room.Bed;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRoomPanel extends ThemedPanel {

    private Frame frame;
    private JLabel roomLabel, smokingLabel, bedNumLabel, bedTypeLabel, occupiedLabel, nightlyRateLabel, pageTitle;
    private RoundedTextField roomField, smokingField, bedNumField, bedTypeField, occupiedField, nightlyRateField;
    private RoundedButton btnCreateRoom;
    private GridBagConstraints gbc = new GridBagConstraints();

    public CreateRoomPanel(Frame frame) {
        setFrame(frame);

        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><h1 style='color:white; font-size:24px; font-weight:bold'>Room Manager</h1></html>");
        roomLabel = new JLabel("<html><p style='color:white; font-size:12px'>Room Number:</p></html>");
        roomField = new RoundedTextField(20);
        smokingLabel = new JLabel("<html><p style='color:white; font-size:12px'>Smoking Preference:</p></html>");
        smokingField = new RoundedTextField(20);
        bedNumLabel = new JLabel("<html><p style='color:white; font-size:12px'>Number of Beds:</p></html>");
        bedNumField = new RoundedTextField(20);
        bedTypeLabel = new JLabel("<html><p style='color:white; font-size:12px'>Bed Type:</p></html>");
        bedTypeField = new RoundedTextField(20);
        occupiedLabel = new JLabel("<html><p style='color:white; font-size:12px'>Occupied Status:</p></html>");
        occupiedField = new RoundedTextField(20);
        nightlyRateLabel = new JLabel("<html><p style='color:white; font-size:12px'>Nightly Rate:</p></html>");
        nightlyRateField = new RoundedTextField(20);

        btnCreateRoom = new RoundedButton("Create Room");

        btnCreateRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getAppController().createRoom(getRoomField(),getBedTypeField(),getBedNumField(),getSmokingField(),getOccupiedField(),getNightlyRateField());
                //System.out.println("Room Created!");
                getFrame().getHomePanel().displayMessage("Your room has been created!", "green");
                getFrame().changeScreen("home");
            }
        });

        gbc = new GridBagConstraints();

        fillLayout();
    }

    private void fillLayout() {
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(pageTitle, gbc);

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(roomLabel, gbc);
        gbc.gridx++;
        add(roomField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(smokingLabel, gbc);
        gbc.gridx++;
        add(smokingField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(bedNumLabel, gbc);
        gbc.gridx++;
        add(bedNumField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(bedTypeLabel, gbc);
        gbc.gridx++;
        add(bedTypeField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(occupiedLabel, gbc);
        gbc.gridx++;
        add(occupiedField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(nightlyRateLabel, gbc);
        gbc.gridx++;
        add(nightlyRateField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(16, 0, 0, 0);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(btnCreateRoom, gbc);

    }
    private void setFrame(Frame frame){
        this.frame = frame;
    }

    private Frame getFrame() {
        return frame;
    }

    private int getRoomField() {
        return Integer.parseInt(roomField.getText());
    }

    private boolean getSmokingField() {
        return Boolean.parseBoolean(smokingField.getText());
    }

    private int getBedNumField() {
        return Integer.parseInt(bedNumField.getText());
    }

    public Bed getBedTypeField() {
        switch(bedTypeField.getText().toLowerCase()){
            case "twin":
                return Bed.TWIN;
            case "full":
                return Bed.FULL;
            case "queen":
                return Bed.QUEEN;
            case "king":
                return Bed.KING;
            default:
                return null;
        }
    }

    private boolean getOccupiedField() {
        return Boolean.parseBoolean(occupiedField.getText());
    }

    private double getNightlyRateField() {
        return Double.parseDouble(nightlyRateField.getText());
    }
}
