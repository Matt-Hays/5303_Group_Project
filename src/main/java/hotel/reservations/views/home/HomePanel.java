/**
 * @file HomePanel.java
 * @author Matthew Hays
 * @brief The custom page *JPanel* that is provided as the initial and
 *        primary page of the application. Provides routing to all workflows.
 * @dependencies Frame.java
 */

package hotel.reservations.views.home;

import hotel.reservations.models.user.Account;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Home Page of the User Interface.
 * Provides user interface page switching to accessible areas of the application depending on a variety of factors
 * such as authentication status, user type, login, logout, etc.
 */
public class HomePanel extends ThemedPanel {
    private Frame frame;
    private JLabel pageHeader;
    private RoundedButton btnLogin, btnRegister, btnSearch, btnAdmin, btnClerk;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean hasPreviousMessage;

    /**
     * View Constructor - Define the view
     * @param frame The user interface frame (JFrame).
     */
    public HomePanel(Frame frame){
        setFrame(frame);

        setLayout(new GridBagLayout());

        btnLogin = new RoundedButton("Login");
        btnRegister = new RoundedButton("Register Account");
        btnSearch = new RoundedButton("Search Rooms");
        pageHeader = new JLabel("<html><h1 style='font-size:28; color:white'>Welcome to the Raven Hotel</h1></html>");
        btnAdmin = new RoundedButton("Admin");
        btnClerk = new RoundedButton("Clerk");


        fillLayout();

        /**
         * Login Action Listener
         */
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("login");
            }
        });

        /**
         * Register Action Listener
         */
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("register");
            }
        });

        /**
         * Search Action Listener
         */
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("search");
            }
        });

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("admin");
                System.out.println("Admin Button Clicked");
            }
        });

        btnClerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasPreviousMessage) clearMessage();
                getFrame().changeScreen("clerk");
            }
        });
    }


    /**
     * Provides display modification dependent on authentication status. Additionally, provides functionality for
     * differing use cases dependent on user type (Guest, Clerk, or Admin). Updates and reuses JButton components
     * by clearing existing ActionListeners and adding new ActionListeners specific to the needs of the user type.
     */
    public void loggedInDisplay(){
        guestLoggedInDisplay();

        if(getFrame().getSession().getUser().getAccountType().equals(Account.ADMIN))
            adminLoggedInDisplay();
        else if(getFrame().getSession().getUser().getAccountType().equals(Account.CLERK))
            clerkLoggedInDisplay();

        revalidate();
        repaint();
    }

    /**
     * Reverts the Home Page display to a logged-out page state.
     * Removes added ActionListeners and re-adds the original ActionListeners on the reusable JButtons.
     */
    public void loggedOutDisplay(){
        remove(btnLogin);
        if(btnAdmin != null) remove(btnAdmin);
        if(btnClerk != null) remove(btnClerk);
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

    /**
     * Given a message to display and the desired (standard named html) color, display the message to the user.
     * @param message The message to be displayed.
     * @param color The standard HTML named color of the text.
     */
    public void displayMessage(String message, String color){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(64, 0, 0, 0);
        add(new JLabel("<html><p style='color:" + color + "'>" + message + "</p></html>"), gbc);
        this.hasPreviousMessage = true;
        revalidate();
        repaint();
    }

    /**
     * The Guest's logged-in display panel.
     */
    public void guestLoggedInDisplay(){
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
                displayMessage("Logged out successfully!", "green");
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
                if(hasPreviousMessage) clearMessage();
                getFrame().getUserPanel().populateAccount(getFrame().getSession().getUser());
                getFrame().changeScreen("account");
            }
        });
        add(btnRegister, gbc, 3);
    }

    /**
     * The Admin's logged-in display panel.
     */
    public void adminLoggedInDisplay(){
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(64, 0, 0, 0);
        add(btnAdmin, gbc);
        revalidate();
        repaint();
    }

    /**
     * The Clerk's logged-in display panel
     */
    public void clerkLoggedInDisplay(){
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(64, 0, 0, 0);
        add(btnClerk, gbc);
        revalidate();
        repaint();
    }
    /**
     * Removes the last message from the panel to ensure additional messages are not displayed on top of each other.
     */
    private void clearMessage(){
        remove(getComponentCount() - 1);
        this.hasPreviousMessage = false;
        revalidate();
        repaint();
    }

    private void clearState(){

    }

    /**
     * Provides the initial grid structure (layout) of the Home Page.
     */
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

    /**
     * Sets the parent JFrame object.
     * @param frame
     */
    private void setFrame(Frame frame){
        this.frame = frame;
    }

    /**
     * @return The parent JFrame object.
     */
    private Frame getFrame() {
        return frame;
    }
}
