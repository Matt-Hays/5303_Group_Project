package hotel.reservations.views.home;

import hotel.reservations.views.GuiHandler;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends ThemedJPanel {
    private GuiHandler guiHandler;
    private JLabel pageHeader;
    private RoundedButton btnLogin, btnRegister, btnSearch;

    /**
     * View Constructor - Define the view
     * @param guiHandler The GUI controller or GUI JFrame.
     */
    public HomePanel(GuiHandler guiHandler){
        setGuiHandler(guiHandler);

        setLayout(new GridBagLayout());

        btnLogin = new RoundedButton("Login");
        btnRegister = new RoundedButton("Register Account");
        btnSearch = new RoundedButton("Search Rooms");
        pageHeader = new JLabel("<html><h1 style='font-size:28; color:white'>Welcome to the Raven Hotel</h1></html>");

        fillLayout();

        /**
         * Swap between views based on button click.
         */
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeView("login");
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeView("register");
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGuiHandler().changeView("search");
            }
        });
    }

    private void fillLayout(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 128, 0);
        add(pageHeader, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(btnLogin, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 48);
        add(btnRegister, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 96, 0, 0);
        add(btnSearch, gbc);
    }

    private void setGuiHandler(GuiHandler guiHandler){
        this.guiHandler = guiHandler;
    }

    private GuiHandler getGuiHandler() {
        return guiHandler;
    }
}
