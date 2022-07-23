package hotel.reservations.controller;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.Admin;
import hotel.reservations.models.user.Clerk;
import hotel.reservations.models.user.Guest;
import hotel.reservations.models.user.IUser;
import hotel.reservations.services.authentication.HotelAuth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PrimaryController implements ApplicationController{
    private ISessionDAO sessionDAO;
    private IUserDAO userDAO;
    private IReservationDAO reservationDAO;
    private IRoomDAO roomDAO;


    public PrimaryController(GUIController guiController, ISessionDAO sessionDAO, IUserDAO userDAO,
                             IReservationDAO reservationDAO, IRoomDAO roomDAO){
        this.guiController=guiController;
        this.sessionDAO=sessionDAO;
        this.userDAO=userDAO;
        this.reservationDAO=reservationDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public void createReservation(IUser guest, Room room, LocalDate arrival, LocalDate departure) {
        // Associate Guest & Room with Reservation.
        // Invoice is created in DAO flow.
        getReservationDAO().createReservation(guest, room, arrival, departure);
        // Return user to the Reservation Page displaying their new Reservation.
    }

    @Override
    public void cancelReservation(Reservation reservation) {
        // Cancel a given reservation.
        getReservationDAO().cancelReservation(reservation);
        // Remove the reservation from the UI.
    }

    @Override
    public void modifyReservation(Reservation modifiedReservation) {
        // Update a given reservation.
        getReservationDAO().modifyReservation(modifiedReservation);
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
    public void modifyRoom(Room modifiedRoom) {
        // Validate User is logged-in & Validate User is a Clerk or an Admin.
        if(getSessionDAO().validateSession(sessionId).equals("Clerk") || getSessionDAO().validateSession(sessionId).equals("Admin")){
            getRoomDAO().modifyRoom(modifiedRoom);
        }
    }

    @Override
    public void logIn(String username, char[] password) {
        // Login the user - retrieve the User object.
        IUser user = getUserDAO().logIn(username, password);
        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // Add the sessionId to the GUI context.

            // Return the user to the Home Page with appropriate buttons.
        }
    }

    @Override
    public void registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String city, String state, String zipCode, GUIController guiController) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Register a new user. Return the new user object.
        IUser user = getUserDAO().createUser(username, HotelAuth.generatePasswordHash(String.valueOf(password)),
                firstName, lastName, address, city, state, zipCode);

        if(user != null){
            // Create a session attaching the user object. Return a UUID sessionId.
            UUID sessionId = getSessionDAO().createSession(user);
            // The user is now logged in. Notify the gui of the change.
            // Pass in the type of user returned from our validate user method.
            guiController.onUserLogin(getUserDAO().validateUser(sessionId));

            // Add the sessionId to the GUI context.
//            guiController.addSessionCtx(sessionId);
//            /**
//             *  Return the user to the Home Page with appropriate buttons.
//             *  This section needs the observer pattern.
//             *  Producer = ApplicationController
//             *  Subscribers = HomePanel & GUIFrame
//             *  On login we want the following to occur:
//             *  --> Home Page: Change Login Button to Logout Button
//             *  --> Home Page: Add a Reservations Route & Button
//             *  --> Reservations Page: to become accessible
//             *  --> Home Page (Clerk+): Add a Modify Rooms Button & Route
//             *  --> Home Page (Admin): Add Create Clerk Button & Route
//             *  --> Home Page (Admin): Add View Report Button & Route
//             */
//            guiController.getHomePage().setLoginBtn("Logout");
//            guiController.getHomePage().setLoginBtn("Reservations");
//            // Determine object type & Add appropriate buttons.
//            if (user instanceof Clerk){
//                guiController.getHomePage().addBtn("Modify Rooms");
//            } else if ( user instanceof Admin){
//                guiController.getHomePage().addBtn("Create Clerk");
//                guiController.getHomePage().addBtn("View Report");
//            }


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
    public void resetPassword(IUser user, char[] oldPassword, char[] newPassword) {
        // Make sure the old password is valid.
        getUserDAO().resetPassword(user, oldPassword, newPassword);
    }

    @Override
    public void modifyUser(IUser modifiedUser) {

    }

    @Override
    public void createClerk(IUser newClerk) {

    }

    @Override
    public void payInvoice(Reservation reservation) {

    }

    private GUIController getGuiController() {
        return guiController;
    }

    private void setGuiController(GUIController guiController) {
        this.guiController = guiController;
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
