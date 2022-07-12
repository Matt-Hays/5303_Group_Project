package com.gof.hr2s.controller;


import com.gof.hr2s.database.Database;

import com.gof.hr2s.models.*;
import com.gof.hr2s.service.HotelAuth;
import com.gof.hr2s.service.HotelModels;
import com.gof.hr2s.service.Response;
import com.gof.hr2s.service.events.controlPanel.*;
import com.gof.hr2s.service.events.createClerkPage.CreateNewClerkListener;
import com.gof.hr2s.service.events.modifyRoom.ModifyRoomListener;
import com.gof.hr2s.service.events.modifyRooms.ModifyRoomsListener;
import com.gof.hr2s.service.events.searchResultsPanel.ReserveRoomListener;
import com.gof.hr2s.service.events.searchRoomsPanel.SearchAvailableRoomsListener;
import com.gof.hr2s.service.events.updateAccountPage.ModifyAccountListener;
import com.gof.hr2s.service.events.loginPage.LoginListener;
import com.gof.hr2s.service.events.loginPage.RegistrationListener;
import com.gof.hr2s.service.events.registrationPage.RegisterListener;
import com.gof.hr2s.ui.HotelViews;

import javax.swing.*;
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
        this.views.addSearchRoomsPageListeners(new SearchAvailableRoomsListener());
        this.views.addControlPageListeners(new ViewAccountListener(), new SearchRoomsListener(),
                new UpdateAccountListener(), new CreateClerkListener(), new com.gof.hr2s.service.events.controlPanel.ModifyRoomsListener());
        this.views.addUpdateAccountPageListeners(new ModifyAccountListener());
        this.views.addModifyRoomListener(new ModifyRoomListener());
        this.views.addCreateClerkPageListeners(new CreateNewClerkListener());
    }

    public static void callNewPage(String newPage) {
        views.changeScreen(newPage);
    }

    // Login User
    public static void login() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Get fields from login page
        String username = views.getUsernameLogin();
        String password = String.valueOf(views.getPasswordLogin());
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
            if(user instanceof Clerk){
                views.toggleModifyRoomsBtn();
            } else if (user instanceof Guest){
                views.toggleCreateClerkBtn();
            }
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
        String guestUsername = views.getGuestUsername();
        // Search for Available Rooms
        ArrayList<Room> availableRooms = models.searchAvailableRooms(arrival, departure);


        // Swap to view rooms screen and populate the screen with the found rooms
        for (Room room : availableRooms) {
            // Populate The Search Results page with room information
            views.createNewLabelSearch(String.valueOf(room.getRoomId()));
            views.createNewLabelSearch(room.getBedType().toString());
            views.createNewLabelSearch(String.valueOf(room.getSmoking()));
            views.createNewLabelSearch(String.valueOf(room.getNumBeds()));
            JButton btn;
            if(guestUsername != null){
                 btn = views.createNewButtonSearch(String.valueOf(room.getRoomId()),
                        String.valueOf(room.getRoomId()), arrival, departure, guestUsername);
            } else {
                 btn = views.createNewButtonSearch(String.valueOf(room.getRoomId()),
                        String.valueOf(room.getRoomId()), arrival, departure);
            }
            views.addSearchResultsPageNewBtnListener(new ReserveRoomListener(), btn);
        }
        views.changeScreen("search-results");
    }

    // Reserve a Room
    public static void makeReservation(String btnParams) {
        String sessionId = views.getSessionId();
        Object user = models.getSessionUser(sessionId);
        String[] btnInputs = btnParams.split(",");
        String roomId = btnInputs[0];
        String arrival = btnInputs[1];
        String departure = btnInputs[2];

        Room room = models.getRoom(roomId);
        if(user instanceof Clerk){
            String guestUsername = btnInputs[3];
            Guest guest = (Guest) models.getUserByUsernameCatalog(guestUsername);
            guest.createReservation(LocalDate.parse(btnInputs[1]), LocalDate.parse(btnInputs[2]), room);
        } else if (user instanceof Guest){
            Guest guest = (Guest) user;
            guest.createReservation(LocalDate.parse(btnInputs[1]), LocalDate.parse(btnInputs[2]), room);
        }
    }

    // Update a User Account
    public static void updateUserAccount() {
        String sessionId = views.getSessionId();
        String newFirstName = views.getFirstNameUpdate();
        String newLastName = views.getLastNameUpdate();

        Object user = models.getSessionUser(sessionId);
        if(user instanceof Clerk){
            Clerk clerk = (Clerk) user;
            clerk.setFirstName(newFirstName);
            clerk.setLastName(newLastName);
            clerk.changePassword(clerk.getUsername(), database.getPassword(clerk.getUsername()),
                    views.getPasswordUpdate());
            clerk.updateUser();
        } else if (user instanceof Guest){
            Guest guest = (Guest) user;
            guest.setFirstName(newFirstName);
            guest.setLastName(newLastName);
            guest.updateUser();
        }
    }

    public static void searchRoomsPageSetup(){
        String sessionId = views.getSessionId();
        Object user = models.getSessionUser(sessionId);
        if(user instanceof Clerk){
            views.toggleSearchRoomsGuestField();
        }
        callNewPage("search-rooms");
    }

    public static void clerkModifyRooms(){
        ArrayList<Room> rooms = models.getAllRooms();
        for(Room room : rooms){
            views.createLabelModifyRooms(String.valueOf(room.getRoomId()));
            views.createLabelModifyRooms(String.valueOf(room.getNumBeds()));
            views.createLabelModifyRooms(String.valueOf(room.getBedType()));
            views.createLabelModifyRooms(String.valueOf(room.getSmoking()));
            views.createLabelModifyRooms(String.valueOf(room.getNightlyRate()));
            JButton btn = views.createNewBtnModifyRooms(String.valueOf(room.getRoomId()),
                    String.valueOf(room.getRoomId()));
            views.addModifyRoomsPageListeners(new ModifyRoomsListener(), btn);
        }
        callNewPage("modify-rooms");
    }

    public static void clerkModifyRoomScreen(String roomId){
        Room room = models.getRoom(roomId);
        views.prePopulateModifyRoomPage(String.valueOf(room.getRoomId()), String.valueOf(room.getBedType()),
                String.valueOf(room.getNumBeds()), String.valueOf(room.getSmoking()), String.valueOf(room.getNightlyRate()));
        callNewPage("modify-room");
    }

    public static void clerkModifyRoom(){
        String sessionId = views.getSessionId();
        Object user = models.getSessionUser(sessionId);
        if(!(user instanceof Clerk)){
            return;
        }
        String roomId = views.getRoomIdModifyRoom();
        String bedType = views.getBedTypeModifyRoom();
        String numBeds = views.getNumberOfBedsModifyRoom();
        String smoking = views.getSmokingModifyRoom();
        String nightlyRate = views.getNightlyRateModifyRoom();

        Room room = models.getRoom(roomId);
        room.setBedType(Bed.valueOf(bedType));
        room.setNumBeds(Integer.parseInt(numBeds));
        room.setSmoking(Boolean.parseBoolean(smoking));
        room.setNightlyRate(Double.parseDouble(nightlyRate));

        models.modifyRoom(room);
        callNewPage("control-panel");
    }

    public static void adminCreateClerkPage(){
        System.out.println("Hello from btn click");
        String sessionId = views.getSessionId();
        Object user = models.getSessionUser(sessionId);
        if(!(user instanceof Guest)){
            return;
        }
        callNewPage("create-clerk");
    }

    public static void createANewClerk() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String sessionId = views.getSessionId();
        Object user = models.getSessionUser(sessionId);
        System.out.println("Method called!");
        if(!(user instanceof Guest)){
            System.out.println("Kicked");
            return;
        }
        System.out.println("Passed if check");
        String username = views.getNewClerkUsername();
        String password = String.valueOf(views.getNewClerkPassword());
        String hashed_password = HotelAuth.generatePasswordHash(password);
        String firstName = views.getNewClerkFirstName();
        String lastName = views.getNewClerkLastName();

        models.createClerk(username, hashed_password, firstName, lastName);
    }
}