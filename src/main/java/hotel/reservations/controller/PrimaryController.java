package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.dao.session.ISessionDAO;
import hotel.reservations.persistence.dao.session.SessionDAO;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.UserService;
import hotel.reservations.services.maps.UserServiceImpl;
import hotel.reservations.persistence.dao.reservation.IReservationDAO;
import hotel.reservations.persistence.dao.reservation.ReservationDAO;
import hotel.reservations.persistence.dao.room.IRoomDAO;
import hotel.reservations.persistence.dao.room.RoomDAO;
import hotel.reservations.persistence.dao.user.IUserDAO;
import hotel.reservations.persistence.dao.user.UserDAO;
import hotel.reservations.views.controller.GuiHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PrimaryController implements ApplicationController{
    private GuiHandler guiHandler;
    private ISessionDAO sessionDAO;
    private IUserDAO userDAO;
    private IReservationDAO reservationDAO;
    private IRoomDAO roomDAO;

    private final UserService userService;


    public PrimaryController(Database db){
        this.guiHandler = null;
        this.sessionDAO = new SessionDAO();
        this.userDAO = new UserDAO(db);
        this.reservationDAO = new ReservationDAO(db);
        this.roomDAO = new RoomDAO(db, reservationDAO);
        this.userService = new UserServiceImpl(this.userDAO, this.sessionDAO);
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
    public UUID logIn(String username, char[] password) {
        return userService.login(username, password);
    }

    @Override
    public UUID registerUser(String username, char[] password, String firstName, String lastName, String street,
                             String state, String zipCode) {
        return userService.createUser(Account.GUEST, username, password, firstName, lastName, street, state, zipCode);
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
