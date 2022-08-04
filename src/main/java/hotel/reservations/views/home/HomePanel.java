package hotel.reservations.views.home;

import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends ThemedPanel {
    private Frame frame;
    private JLabel pageHeader;
    private RoundedButton btnLogin, btnRegister, btnSearch;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousMessage;

    /**
     * View Constructor - Define the view
     * @param frame The GUI controller or GUI JFrame.
     */
    public HomePanel(Frame frame){
        setFrame(frame);

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
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("login");
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("register");
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("search");
            }
        });
    }

    public void displayNewUserMessage(){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(new JLabel("<html><p style='color:green'>Welcome to the hotel!</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    public void loggedInDisplay(){
        remove(btnLogin);
        btnLogin.setText("Logout");
        btnLogin.setPreferredSize(new Dimension(btnLogin.getWidth() + 12, btnLogin.getHeight()));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        // Remove previous action listeners.
        for(ActionListener al : btnLogin.getActionListeners()){
            btnLogin.removeActionListener(al);
        }
        add(btnLogin, gbc, 2);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().getAppController().logOut(getFrame().getSession().getId());
                displayMessage("Logged out successfully!");
                loggedOutDisplay();
            }
        });

        btnRegister.setText("Account");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 48);
        for(ActionListener al : btnRegister.getActionListeners()){
            btnRegister.removeActionListener(al);
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getUserPanel().populateAccount(getFrame().getSession().getUser());
                getFrame().changeScreen("account");
            }
        });
        add(btnRegister, gbc, 3);

        revalidate();
        repaint();
    }

    public void loggedOutDisplay(){
        remove(btnLogin);
        btnLogin.setText("Login");
        btnLogin.setPreferredSize(new Dimension(btnLogin.getWidth() - 12, btnLogin.getHeight()));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        // Remove previous action listeners.
        for(ActionListener al : btnLogin.getActionListeners()){
            btnLogin.removeActionListener(al);
        }

        add(btnLogin, gbc, 2);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("login");
            }
        });

        btnRegister.setText("Register");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 48);
        for(ActionListener al : btnRegister.getActionListeners()){
            btnRegister.removeActionListener(al);
        }

        add(btnRegister, gbc, 3);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("register");
            }
        });

        revalidate();
        repaint();
    }

    private void displayMessage(String message){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(64, 0, 0, 0);
        add(new JLabel("<html><p style='color:green'>" + message + "</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    private void clearMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    private void fillLayout(){
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 128, 0);
        add(pageHeader, gbc);
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

    private void setFrame(Frame frame){
        this.frame = frame;
    }

    private Frame getFrame() {
        return frame;
    }
}
