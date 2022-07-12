package com.gof.hr2s.controller;


import com.gof.hr2s.database.Database;

import com.gof.hr2s.ui.loginPage.LoginListener;
import com.gof.hr2s.ui.loginPage.RegistrationListener;
import com.gof.hr2s.services.events.registerPage.RegisterListener;
import com.gof.hr2s.ui.HotelViews;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class AppController {
    // Views
    private static HotelViews views;
    // Models
//    private static HotelModels models;
    // Database
    private static Database database;


    public AppController(HotelViews views) throws NoSuchMethodException {
        this.views = views;
//        this.models = models;
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
    }

    public static void callNewPage(String newPage) {
        views.changeScreen(newPage);
    }

    // Login User
    public static void login() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        views.setLoginPageTitle("This is a new Title!!!");
//         Get fields from login page
//        String username = views.getUsernameLogin();
//        String password = views.getPasswordLogin().toString();
//
//        // Find the user record in the db - get the hashed password
//        String hashedPw = database.getPassword(username);
//        // Authenticate the password
//        if (HotelAuth.validatePassword(password, hashedPw)) {
//            // Create and store a session
//            // Return session id to GUI
//            // Swap page to Control Page
//        }
    }

    // Register a new User
    public static void registerUser() {
        String username = views.getUsernameRegister();
        String password = String.valueOf(views.getPasswordRegister());
        String firstName = views.getFirstNameRegister();
        String lastName = views.getLastNameRegister();
        String address1 = views.getAddress1Register();
        String address2 = views.getAddress2Register();
        String city = views.getCityRegister();
        String state = views.getStateRegister();
        String zip = views.getZipRegister();

        // Create a User object.
        // Create a Session object with the User attached.
        // Add the User to the Database.
        // Redirect the User to the Control Panel.
    }

    // Search for available Rooms
    public static void searchAvailableRooms() {
    }

    // Reserve a Room
    public static void makeReservation() {
    }

    // Update a User Account
    public static void updateUserAccount() {
    }
}