package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.ISessionDAO;
import hotel.reservations.models.session.SessionDAO;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.reservationDAO.IReservationDAO;
import hotel.reservations.services.reservationDAO.ReservationDAO;
import hotel.reservations.services.roomDAO.IRoomDAO;
import hotel.reservations.services.roomDAO.RoomDAO;
import hotel.reservations.services.UserDAO.IUserDAO;
import hotel.reservations.services.UserDAO.UserDAO;
import hotel.reservations.views.controller.GuiHandler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PrimaryController implements ApplicationController{
    private GuiHandler guiHandler;
    private ISessionDAO sessionDAO;
    private IUserDAO userDAO;
    private IReservationDAO reservationDAO;
    private IRoomDAO roomDAO;


    public PrimaryController(Database db){
        this.guiHandler = null;
        this.sessionDAO = new SessionDAO();
        this.userDAO = new UserDAO(db);
        this.reservationDAO = new ReservationDAO(db);
        this.roomDAO = new RoomDAO(db, reservationDAO);
    }

    @Override
    public void addViewsHandler(GuiHandler guiHandler){
        this.guiHandler = guiHandler;
    }

    @Override
    public void createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        reservationDAO.createReservation(guest, room, arrival, departure);
    }

    @Override
    public void cancelReservation(Reservation reservation) {

    }

    @Override
    public void modifyReservation(Reservation modifiedReservation) {

    }

    @Override
    public void checkIn(Reservation reservation) {

    }

    @Override
    public void checkOut(Reservation reservation) {

    }

    /**
     * RESERVATION ROUTES BEGIN
     */
//    @Override
//    public void createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
//        // Associate Guest & Room with Reservation.
//        // Invoice is created in DAO flow.
//        Reservation reservation = getReservationDAO().createReservation(guest, room, arrival, departure);
//        // Return user to the Reservation Page displaying their new Reservation.
//        getGuiHandler().setHomePanel("Admin");
//    }
//
//    @Override
//    public void cancelReservation(Reservation reservation) {
//        // Cancel a given reservation.
//        getReservationDAO().cancelReservation(reservation);
//        // Remove the reservation from the UI.
//    }
//
//    @Override
//    public void modifyReservation(Reservation modifiedReservation) {
//        // Update a given reservation. Return the updated Reservation.
//        Reservation reservation = getReservationDAO().modifyReservation(modifiedReservation);
//        // Display the updated result in the UI.
//    }
//
//    @Override
//    public void checkIn(Reservation reservation) {
//        // Check in a user.
//        getReservationDAO().checkIn(reservation);
//        // Update the reservation checked-in field & date in the UI.
//    }
//
//    @Override
//    public void checkOut(Reservation reservation) {
//        // Check out a user.
//        getReservationDAO().checkOut(reservation);
//        // // Update the reservation checked-in field & date in the UI.
//    }

    @Override
    public void viewReport(UUID sessionId) {
        // Validate User is logged-in & Validate User is an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Admin")){
            // Generate the List.
            // Update the Report Page with details.
        };
    }

//    @Override
//    public void getInvoice(Reservation reservation) {
//        // Get the invoice a reservation.
//        getReservationDAO().getInvoice(reservation);
//    }

    @Override
    public void payInvoice(Reservation reservation) {

    }

    /**
     * ROOM ROUTES BEGIN
     */
    @Override
    public void addRoom(Room newRoom) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
//        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
//            getRoomDAO().createRoom(newRoom);
//            // Update the Room
//        }
    }

    @Override
    public void deleteRoom(Room room) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
//        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
//            getRoomDAO().deleteRoom(room);
//        }
    }

    @Override
    public void modifyRoom(Room modifiedRoom) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
//        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
//            getRoomDAO().modifyRoom(modifiedRoom);
//        }
    }

    @Override
    public void viewStatus(List<Room> roomReport) {
        // Get all rooms in a List<Room>. Sort the list based on checked-in (currently in-use) 1st and reserved
        // status 2nd.
    }

    /**
     * USER ROUTES BEGIN
     */
    @Override
    public void logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Login the user - retrieve the User object.
        User user = getUserDAO().logIn(username, password);

        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // Add the sessionId to the GUI context.
            getGuiHandler().setSessionCtx(sessionId);
            // Pass in the type of user returned from our validate user method to allow the view to properly
            // configure the Home Panel based on user type.
            getGuiHandler().setHomePanel(getSessionDAO().validateSession(sessionId));
            // Return the user to the Home Page.
            getGuiHandler().changeScreen("home");
        }
    }

    @Override
    public void registerUser(String username, char[] password, String firstName, String lastName, String street,
                             String state, String zipCode) {
        // Register a new user. Return the new user object.
        User user = null;
        try {
            user = getUserDAO().createUser(Account.GUEST, username, password, firstName, lastName, street, state, zipCode);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // The user is now logged in. Notify the gui of the change.
            getGuiHandler().setSessionCtx(sessionId);
            // Pass in the type of user returned from our validate user method to allow the view to properly
            // configure the Home Panel based on user type.
            getGuiHandler().setHomePanel(getSessionDAO().validateSession(sessionId));
            // Return the user to the Home Page.
            getGuiHandler().changeScreen("home");
        }
    }

    @Override
    public void getInvoice(Reservation reservation) {

    }

    @Override
    public void resetPassword(String username, char[] oldPassword, char[] newPassword) {
        // Reset the user's password in the database.
        getUserDAO().changePassword(username, oldPassword, newPassword);
        // Return the user to the Home Page with appropriate buttons.
        getGuiHandler().setHomePanel(getSessionDAO().validateSession(getGuiHandler().getSessionCtx()));
        getGuiHandler().changeScreen("home");
    }

    @Override
    public void modifyUser(String newUsername, String firstName, String lastName, String street, String state,
                           String zipCode, boolean active) {
        // Modify the user
        UUID userSessionId = getGuiHandler().getSessionCtx();
        UUID sessionUserId = getSessionDAO().getSessionUser(userSessionId).getUserId();
        getUserDAO().updateUser(sessionUserId, newUsername, firstName, lastName, street, state, zipCode, active);
        // Return the user to their Profile Page with updated values
    }

    @Override
    public void createClerk(String username, String firstName, String lastName, String street, String state,
                            String zipCode) {
        // Restrict route to only Admin
        if(getSessionDAO().validateSession(getGuiHandler().getSessionCtx()).equals("Admin")){
            getUserDAO().createDefaultUser(Account.CLERK, username, firstName, lastName, street, state, zipCode);
            // Flash a success message to the Admin and clear the textFields on the page.
        }
        return;
    }

    /**
     * APP CONTROLLER PRIVATE ACCESS METHODS
     */
    private GuiHandler getGuiHandler() {
        return guiHandler;
    }

    private void setGuiController(GuiHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    private ISessionDAO getSessionDAO() {
        return sessionDAO;
    }

    private void setSessionDAO(ISessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    private IUserDAO getUserDAO() {
        return userDAO;
    }

    private void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
//
//    private IReservationDAO getReservationDAO() {
//        return reservationDAO;
//    }
//
//    private void setReservationDAO(IReservationDAO reservationDAO) {
//        this.reservationDAO = reservationDAO;
//    }
//
//    private IRoomDAO getRoomDAO() {
//        return roomDAO;
//    }
//
//    private void setRoomDAO(IRoomDAO roomDAO) {
//        this.roomDAO = roomDAO;
//    }
}
