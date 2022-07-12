package com.gof.hr2s.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class HotelViews extends JFrame {
    public JPanel cards; //a panel that uses CardLayout
    public UserLogin loginPanel;
    public GuestRegistration registrationPanel;
//    public UserPanel userPanel;
    public ControlPanel controlPanel;

    public String sessionId = null;

    public SearchRooms searchRoomsPanel;
    public SearchResults searchResultsPanel;

    public HotelViews() {
        //Create the "cards".
        loginPanel = new UserLogin();
        registrationPanel = new GuestRegistration();
        controlPanel = new ControlPanel();
        searchRoomsPanel = new SearchRooms();
        searchResultsPanel = new SearchResults();
    }

    private void addComponentToPane(Container pane) {
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, "login");
        cards.add(registrationPanel, "registration");
        cards.add(controlPanel, "control-panel");
        cards.add(searchRoomsPanel, "search-rooms");
        cards.add(searchResultsPanel, "search-results");

        pane.add(cards, BorderLayout.CENTER);
    }

    // User Login Event Listeners
    public void addLoginPageListeners(ActionListener loginListener, ActionListener registerListener) {
        loginPanel.addLoginListener(loginListener);
        loginPanel.addRegistrationListener(registerListener);
    }

    // User Panel Event Listeners
//    public void addControlPageListeners(ActionListener viewAccountListener,
//                                        ActionListener searchRoomListener, ActionListener logoutListener) {
//        userPanel.addViewAccountEventListener(viewAccountListener);
//        userPanel.addReserveRoomEventListener(reserveRoomListener);
//        userPanel.addSearchRoomsEventListener(searchRoomListener);
//        userPanel.addLogoutEventListener(logoutListener);
//    }

    public void addControlPageListeners(ActionListener viewAccountListener, ActionListener searchRoomsListener){
        controlPanel.addViewAccountListener(viewAccountListener);
        controlPanel.addSearchRoomsListener(searchRoomsListener);
    }

    public void addRegisterPageListeners(ActionListener registerListener) {
        registrationPanel.addRegisterListener(registerListener);
    }

    public void addSearchRoomsPageListeners(ActionListener searchRoomsListener) {
        searchRoomsPanel.addSearchRoomsListener(searchRoomsListener);
    }

    private void constructGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Create and set up the window.
        setTitle("Hotel Reservation System");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        //Create and set up the content pane.
        addComponentToPane(getContentPane());

        //Display the window.
        pack();
        setVisible(true);
    }

    public void changeScreen(String screen) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, screen);
    }

    public void startGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                constructGUI();
            }
        });
    }

    // Get Fields from login page
    public String getUsernameLogin() {
        return loginPanel.getUsernameField();
    }

    public char[] getPasswordLogin() {
        return loginPanel.getPasswordField();
    }

    public void setLoginPageTitle(String newTitle) {
        this.loginPanel.setPanelTitle(newTitle);
    }

    // Get fields from register page
    public String getUsernameRegister() {
        return registrationPanel.getUsernameField();
    }

    public char[] getPasswordRegister() {
        return registrationPanel.getPasswordField();
    }

    public String getFirstNameRegister() {
        return registrationPanel.getFirstNameField();
    }

    public String getLastNameRegister() {
        return registrationPanel.getLastNameField();
    }

    public String getAddress1Register() {
        return registrationPanel.getAddress1();
    }

    public String getAddress2Register() {
        return registrationPanel.getAddress2();
    }

    public String getCityRegister() {
        return registrationPanel.getCity();
    }

    public String getStateRegister() {
        return registrationPanel.getState();
    }

    public String getZipRegister() {
        return registrationPanel.getZip();
    }

    public String getArrivalSearch() {
        return searchRoomsPanel.getArrival();
    }

    public String getDepartureSearch() {
        return searchRoomsPanel.getDeparture();
    }

    public void createNewLabelSearch(String newLabel) {
        searchResultsPanel.createTextField(newLabel);
    }

    public void setSessionId(String newSessionId){
        this.sessionId = newSessionId;
    }

    public String getSessionId(){
        return this.sessionId;
    }
}