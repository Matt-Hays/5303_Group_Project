package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.ISessionDAO;
import hotel.reservations.models.user.User;
import hotel.reservations.services.UserDAO.IUserDAO;
import hotel.reservations.services.UserDAO.UserDAO;
import hotel.reservations.services.authentication.HotelAuth;
import hotel.reservations.views.GuiHandler;

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


    public PrimaryController(GuiHandler guiHandler, ISessionDAO sessionDAO, UserDAO userDAO,
                             IReservationDAO reservationDAO, IRoomDAO roomDAO){
        this.guiHandler=guiHandler;
        this.sessionDAO=sessionDAO;
        this.userDAO=userDAO;
        this.reservationDAO=reservationDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public void createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        // Associate Guest & Room with Reservation.
        // Invoice is created in DAO flow.
        Reservation reservation = getReservationDAO().createReservation(guest, room, arrival, departure);
        // Return user to the Reservation Page displaying their new Reservation.
        getGuiHandler().setHomePanel("Admin");
    }

    @Override
    public void cancelReservation(Reservation reservation) {
        // Cancel a given reservation.
        getReservationDAO().cancelReservation(reservation);
        // Remove the reservation from the UI.
    }

    @Override
    public void modifyReservation(Reservation modifiedReservation) {
        // Update a given reservation. Return the updated Reservation.
        Reservation reservation = getReservationDAO().modifyReservation(modifiedReservation);
        // Display the updated result in the UI.
    }

    @Override
    public void checkIn(Reservation reservation) {
        // Check in a user.
        getReservationDAO().checkIn(reservation);
        // Update the reservation checked-in field & date in the UI.
    }

    @Override
    public void checkOut(Reservation reservation) {
        // Check out a user.
        getReservationDAO().checkOut(reservation);
        // // Update the reservation checked-in field & date in the UI.
    }

    @Override
    public void viewReport() {

    }

    @Override
    public void viewReport(UUID sessionId) {
        // Validate User is logged-in & Validate User is an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Admin")){
            // Generate the List.
            // Update the Report Page with details.
        };
    }

    @Override
    public void addRoom(Room newRoom) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
            getRoomDAO().createRoom(newRoom);
            // Update the Room
        }
    }

    @Override
    public void deleteRoom(Room room) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
            getRoomDAO().deleteRoom(room);
        }
    }

    @Override
    public void modifyRoom() {

    }

    @Override
    public void modifyRoom(Room modifiedRoom) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
            getRoomDAO().modifyRoom(modifiedRoom);
        }
    }

    @Override
    public void logIn(String username, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Login the user - retrieve the User object.
        User user = getUserDAO().logIn(username, password);

        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // Add the sessionId to the GUI context.

            // Return the user to the Home Page with appropriate buttons.
        }
    }

    @Override
    public void registerUser() {

    }

    @Override
    public void registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String city, String state, String zipCode) throws NoSuchAlgorithmException
            , InvalidKeySpecException {

        // Register a new user. Return the new user object.
        User user = getUserDAO().createUser(username, HotelAuth.generatePasswordHash(String.valueOf(password)),
                firstName, lastName, address, city, state, zipCode);

        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // The user is now logged in. Notify the gui of the change.
            // Pass in the type of user returned from our validate user method.
            getGuiHandler().setHomePanel(getSessionDAO().validateUser(sessionId));
        }
    }

    @Override
    public void getInvoice(Reservation reservation) {
        // Get the invoice a reservation.
        getReservationDAO().getInvoice(reservation);
    }

    @Override
    public void viewStatus(List<Room> roomReport) {
        // Get all rooms in a List<Room>. Sort the list based on checked-in (currently in-use) 1st and reserved
        // status 2nd.
    }

    @Override
    public void resetPassword() {

    }

    @Override
    public void resetPassword(User user, char[] oldPassword, char[] newPassword) {
        // Make sure the old password is valid.
        getUserDAO().resetPassword(user, oldPassword, newPassword);
    }

    @Override
    public void modifyUser() {

    }

    @Override
    public void modifyUser(User modifiedUser) {

    }

    @Override
    public void createClerk() {

    }

    @Override
    public void createClerk(User newClerk) {

    }

    @Override
    public void payInvoice() {

    }

    @Override
    public void payInvoice(Reservation reservation) {

    }

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

    private IReservationDAO getReservationDAO() {
        return reservationDAO;
    }

    private void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    private IRoomDAO getRoomDAO() {
        return roomDAO;
    }

    private void setRoomDAO(IRoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }
}
