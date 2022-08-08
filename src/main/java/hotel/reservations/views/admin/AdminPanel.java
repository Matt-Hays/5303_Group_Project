/**
 * @file AdminPanel.java
 * @brief The custom page *JPanel* that is provided to allow the Admin
 *        to complete their workflows.
 * @author Matthew Hays
 * @dependencies Frame.java
 */

package hotel.reservations.views.admin;

import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends ThemedPanel {
    private final Frame frame;
    private JLabel pageTitle;
    private RoundedButton btnCreateClerk, btnResetGuestPassword;
    private boolean hasPreviousMessage;

    /**
     * Layout the Admin panel's content using a GridBagLayout.
     * @param frame Frame.java interface dependency.
     */
    public AdminPanel(Frame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        pageTitle = new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>Admin Page</p></html>");
        btnCreateClerk = new RoundedButton("Create Clerk");
        btnResetGuestPassword = new RoundedButton("Reset Guest Password");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pageTitle, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(64, 0, 0, 0);
        add(btnCreateClerk, gbc);
        gbc.gridx++;
        add(btnResetGuestPassword, gbc);

        btnCreateClerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessages();
                // Set up Register Page for an Admin
                getFrame().getRegisterPanel().prepareAdminDisplay();
                // Swap to the setup Register page.
                getFrame().changeScreen("register");
            }
        });

        btnResetGuestPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessages();
                // Route to a reset password page.
                getFrame().changeScreen("reset-guest-credentials");
            }
        });
    }

    /**
     * Remove the most recently added component.
     * *Assumption: The last component added was the displayed message.
     */
    private void clearMessages(){
        remove(getComponentCount() - 1);
        hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    private Frame getFrame(){
        return  this.frame;
    }
}
