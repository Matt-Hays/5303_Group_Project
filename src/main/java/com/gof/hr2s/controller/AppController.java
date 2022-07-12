package com.gof.hr2s.controller;


import com.gof.hr2s.database.Database;

import com.gof.hr2s.models.Account;
import com.gof.hr2s.models.Reservation;
import com.gof.hr2s.models.Room;
import com.gof.hr2s.service.HotelAuth;
import com.gof.hr2s.service.HotelModels;
import com.gof.hr2s.service.events.controlPanel.SearchRoomsListener;
import com.gof.hr2s.service.events.controlPanel.ViewAccountListener;
import com.gof.hr2s.service.events.loginPage.LoginListener;
import com.gof.hr2s.service.events.loginPage.RegistrationListener;
import com.gof.hr2s.service.events.registrationPage.RegisterListener;
import com.gof.hr2s.ui.HotelViews;
import org.springframework.security.core.userdetails.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;


public class AppController {
    // Views
    private static HotelViews views;
    // Models
    private static HotelModels models;
    // Database
    private static Database database;


    public AppController(HotelModels models, HotelViews views, Database db) throws NoSuchMethodException {
        this.views = views;
        this.models = models;
        this.database = db;
        initApp();
    }

    private void initApp() throws NoSuchMethodException {
        startGUI();
        addActionListeners();
    }

    private void startGUI() {
        this.views.startGUI();
    }

    private void addActionListeners() throws NoSuchMethodException {
        // User Panel Event Listeners
//        this.views.addControlPageListeners(new ViewAccountListener(), new SearchRoomsListener(), new LogoutListener());
        this.views.addRegisterPageListeners(new RegisterListener());
        // User Login Event Listeners
        this.views.addLoginPageListeners(new LoginListener(), new RegistrationListener());
        // Guest Registration Event Listeners

        this.views.addSearchRoomsPageListeners(new SearchRoomsListener());
        this.views.addControlPageListeners(new ViewAccountListener(), new SearchRoomsListener());
    }

    public static void callNewPage(String newPage) {
        views.changeScreen(newPage);
    }

    // Login User
    public static void login() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        views.setLoginPageTitle("This is a new Title!!!");
//         Get fields from login page
        String username = views.getUsernameLogin();
        String password = String.valueOf(views.getPasswordLogin());
//
        // Find the user record in the db - get the hashed password
        String hashedPw = database.getPassword(username);
        Object user = models.getUserByUsernameCatalog(username);

        if(hashedPw == null || hashedPw.isEmpty()) {
            return;
        } else if (HotelAuth.validatePassword(password, hashedPw)) {
            // Create and store a session
            UUID sessionId = models.createNewSession(user);
            // Return session id to GUI
            views.setSessionId(String.valueOf(sessionId));
            // Swap page to Control Page
            views.changeScreen("control-panel");
        }
    }

    // Register a new User
    public static void registerUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String username = views.getUsernameRegister();
        String password = String.valueOf(views.getPasswordRegister());
        String firstName = views.getFirstNameRegister();
        String lastName = views.getLastNameRegister();

        // Hash the password
        String hashed_password = HotelAuth.generatePasswordHash(password);

        // Create a Guest object.
        models.createGuest(Account.GUEST, username, hashed_password, firstName, lastName, true);
        // Create a Session object with the User attached.
        Object user = models.getUserByUsernameCatalog(username);
        // Create and store a session
        UUID sessionId = models.createNewSession(user);
        // Return session id to GUI
        views.setSessionId(String.valueOf(sessionId));
        // Redirect the User to the Control Panel.
        views.changeScreen("control-panel");
    }

    // Search for available Rooms
    public static void searchAvailableRooms() {
        // Get arrival and departure dates from UI
        LocalDate arrival = LocalDate.parse(views.getArrivalSearch());
        LocalDate departure = LocalDate.parse(views.getDepartureSearch());
        // Search for Available Rooms
        ArrayList<Room> availableRooms = models.searchAvailableRooms(arrival, departure);

        // Swap to view rooms screen and populate the screen with the found rooms
        for (int i = 0; i < 20; i++) {
            // Populate The Search Results page with room information
            views.createNewLabelSearch("New Label");
        }
        views.changeScreen("search-results");
    }

    // Reserve a Room
    public static void makeReservation() {
    }

    // Update a User Account
    public static void updateUserAccount() {

    }
}