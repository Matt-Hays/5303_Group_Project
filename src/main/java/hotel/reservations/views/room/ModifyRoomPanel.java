/**
 * @file ModifyRoomPanel.java
 * @author Sarah Smallwood
 * @brief The custom page *JPanel* that provides the Clerk access to their
 *        Modify Room workflow.
 * @dependencies Frame.java
 */

package hotel.reservations.views.room;

import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

public class ModifyRoomPanel extends ThemedPanel {
    private Frame frame;
    private JLabel roomInputLabel, roomLabel, smokingLabel, bedNumLabel, bedTypeLabel, occupiedLabel, nightlyRateLabel, pageTitle;
    private RoundedTextField roomInputField, roomField, smokingField, bedNumField, bedTypeField, occupiedField, nightlyRateField;
    private RoundedButton btnReturnRoom, btnModifyRoom;
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Attach the Frame.java dependency and generate all page components.
     * @param frame Frame.java interface.
     */
    public ModifyRoomPanel(Frame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><h1 style='color:white; font-size:24px; font-weight:bold'>Modify Room</h1></html>");
        roomInputLabel = new JLabel("<html><p style='color:white; font-size:12px'>Room Number to Modify:</p></html>");
        roomInputField = new RoundedTextField(20);
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

        btnReturnRoom = new RoundedButton("Return Room Details");
        btnModifyRoom = new RoundedButton("Modify Room");

        btnReturnRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Room room = getFrame().getAppController().getRoom(getRoomInputField());
                populateRoomDetails(room);
                //System.out.println("Room"+getRoomInputField());
            }
        });

        btnModifyRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getAppController().updateRoom(getRoomField(),getBedTypeField(),getBedNumField(),getSmokingField(),getOccupiedField(),getNightlyRateField());
                //System.out.println("Room Created!");
                getFrame().getHomePanel().displayMessage("Your room has been updated!", "green");
                getFrame().changeScreen("home");
            }
        });

        gbc = new GridBagConstraints();
        fillLayout();
    }

    /**
     * Set the values of each component using the given Room domain object.
     * @param room Room.java domain object.
     */
    public void populateRoomDetails(Room room){
        roomField.setText(String.valueOf(room.getRoomId()));
        smokingField.setText(String.valueOf(room.getSmoking()));
        bedNumField.setText(String.valueOf(room.getNumBeds()));
        bedTypeField.setText(String.valueOf(room.getBedType()));
        occupiedField.setText(String.valueOf(room.getOccupied()));
        nightlyRateField.setText(String.valueOf(room.getNightlyRate()));
        revalidate();
        repaint();
    }

    /**
     * Layout the components of the panel.
     */
    private void fillLayout() {
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(pageTitle, gbc);

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        add(roomInputLabel, gbc);
        gbc.gridx++;
        add(roomInputField, gbc);
        gbc.gridx--;

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(btnReturnRoom, gbc);

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
        add(btnModifyRoom, gbc);

    }

    /**
     * Standard getter and setter methods from here through the end of the file.
     */
    private void setFrame(Frame frame){
        this.frame = frame;
    }

    private Frame getFrame() {
        return frame;
    }

    private int getRoomInputField() {
        return Integer.parseInt(roomInputField.getText());
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

