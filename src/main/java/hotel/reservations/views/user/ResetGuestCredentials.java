/**
 * @file ResetGuestCredentials.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that provides Clerk access to their
 *        reset guest password workflow.
 * @dependencies Frame.java
 */

package hotel.reservations.views.user;

import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.RoundedTextField;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class ResetGuestCredentials extends ThemedPanel {
    private final Frame frame;
    private JLabel pageTitle, guestUsername;
    private RoundedTextField guestUsernameField;
    private RoundedButton btnResetPassword, btnBack;

    public ResetGuestCredentials(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());

        pageTitle = new JLabel("<html><p style='color:white; font-size:24px; font-weight:bold'>Reset Guest Password</p></html>");
        guestUsername = new JLabel("<html><p style='color:white; font-size:16px'>Guest Username</p></html>");
        guestUsernameField = new RoundedTextField(20);
        btnResetPassword = new RoundedButton("Generate Default Password");
        btnBack = new RoundedButton("Back");

        btnResetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UUID sessionId = getFrame().getSession().getId();
                getFrame().getAppController().resetGuestPassword(sessionId, getGuestUsernameField());
                getFrame().getHomePanel().displayMessage("Guest: " + getGuestUsernameField() + "'s password has " +
                                                                  "been successfully reset.", "green");
                getFrame().changeScreen("home");
            }
        });

        fillLayout();
    }

    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(pageTitle);
        gbc.gridy++;
        add(guestUsername, gbc);
        gbc.gridx++;
        add(guestUsernameField, gbc);
        gbc.gridx--;
        gbc.gridy++;
        add(btnResetPassword, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx++;
        add(btnBack, gbc);
    }

    private Frame getFrame() {
        return frame;
    }

    public String getGuestUsernameField() {
        return guestUsernameField.getText();
    }

    public void setGuestUsernameField(String guestUsernameField) {
        this.guestUsernameField.setText(guestUsernameField);
    }
}
