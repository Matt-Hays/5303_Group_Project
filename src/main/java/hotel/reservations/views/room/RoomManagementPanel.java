/**
 * @file CreateRoomPanel.java
 * @author Sarah Smallwood
 * @brief The custom page *JPanel* that provides the Clerk access to their
 *        Room Management workflow.
 * @dependencies Frame.java
 */

package hotel.reservations.views.room;

import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomManagementPanel extends ThemedPanel {

    private Frame frame;
    private JLabel roomLabel, smokingLabel, bedNumLabel, bedTypeLabel, occupiedLabel, nightlyRateLabel, pageTitle;
    private RoundedTextField roomField, smokingField, bedNumField, bedTypeField, occupiedField, nightlyRateField;
    private RoundedButton btnCreateRoom, btnModifyRoom, btnDeleteRoom;
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Attach the Frame.java dependency and generate all page components.
     * @param frame Frame.java interface.
     */
    public RoomManagementPanel(Frame frame) {
        setFrame(frame);

        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><h1 style='color:white; font-size:24px; font-weight:bold'>Room Manager</h1></html>");
        roomLabel = new JLabel("<html><p style='color:white; font-size:12px'>Room Type:</p></html>");
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

        btnCreateRoom = new RoundedButton("Create New Room");
        btnModifyRoom = new RoundedButton("Modify Existing Room");
        btnDeleteRoom = new RoundedButton("Delete Existing Room");

        btnCreateRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("createRoom");
                System.out.println("Create Room Button Clicked");
            }
        });

        btnModifyRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("modifyRoom");
                System.out.println("Modify Room Button Clicked");
            }
        });

        btnDeleteRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("deleteRoom");
                System.out.println("Delete Room Button Clicked");
            }
        });

        gbc = new GridBagConstraints();

        fillLayout();
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
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCreateRoom, gbc);

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnModifyRoom, gbc);

        gbc.insets = new Insets(2, 0, 2, 0);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnDeleteRoom, gbc);

    }
    private void setFrame(Frame frame){
        this.frame = frame;
    }
    private Frame getFrame(){
        return frame;
    }
}
