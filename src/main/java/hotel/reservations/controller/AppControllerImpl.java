package hotel.reservations.controller;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.persistence.dao.SessionDao;
import hotel.reservations.persistence.dao.UserDao;
import hotel.reservations.persistence.dao.impls.ReservationDaoImpl;
import hotel.reservations.persistence.dao.impls.RoomDaoImpl;
import hotel.reservations.persistence.dao.impls.SessionDaoImpl;
import hotel.reservations.persistence.dao.impls.UserDaoImpl;
import hotel.reservations.services.ReportService;
import hotel.reservations.services.ReservationService;
import hotel.reservations.services.SearchService;
import hotel.reservations.services.UserService;
import hotel.reservations.services.impls.ReportServiceImpl;
import hotel.reservations.services.impls.ReservationServiceImpl;
import hotel.reservations.services.impls.SearchServiceImpl;
import hotel.reservations.services.impls.UserServiceImpl;
import hotel.reservations.views.frame.Frame;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppControllerImpl implements AppController{
    private Frame guiHandler;
    private SessionDao sessionDAO;
    private UserDao userDAO;
    private ReservationDaoImpl reservationDAO;
    private RoomDao roomDAO;

    private final UserService userService;
    private final SearchService searchService;
    private final ReservationService reservationService;
    private final ReportService reportService;


    public AppControllerImpl(DatabaseImpl db){
        this.guiHandler = null;
        this.sessionDAO = new SessionDaoImpl();
        this.userDAO = new UserDaoImpl(db);
        this.reservationDAO = new ReservationDaoImpl(db);
        this.roomDAO = new RoomDaoImpl(db, reservationDAO);


        /**
         * Services
         */

        this.userService = new UserServiceImpl(this.userDAO, this.sessionDAO);
        this.searchService  = new SearchServiceImpl(this.roomDAO);
        this.reservationService = new ReservationServiceImpl(this.reservationDAO);
        this.reportService = new ReportServiceImpl(this.reservationDAO, this.userDAO);
    }

    /**                       *
     * User Service Endpoints *
     *                        */

    @Override
    public Session registerUser(String username, char[] password, String firstName, String lastName, String street,
                                String state, String zipCode) {
        return userService.createUser(Account.GUEST, username, password, firstName, lastName, street, state, zipCode);
    }

    @Override
    public Session logIn(String username, char[] password) {
        return userService.login(username, password);
    }

    @Override
    public void logOut(UUID id){
        userService.logout(id);
    }

    @Override
    public void resetPassword(String username, char[] oldPassword, char[] newPassword) {
        userService.updatePassword(username, oldPassword, newPassword);
    }

    @Override
    public User modifyUser(UUID sessionId, String newUsername, String firstName, String lastName, String street, String state,
                           String zipCode, boolean active) {
        return userService.updateUser(sessionId, newUsername, firstName, lastName, street, state, zipCode, active);
    }

    @Override
    public void createClerk(String username, String firstName, String lastName, String street, String state,
                            String zipCode) {
        userService.createClerk(username, firstName, lastName, street, state, zipCode);
    }

    @Override
    public void resetGuestPassword(UUID sessionId, String username){
        userService.resetGuestPassword(sessionId, username);
    }

    /**                              *
     * End of User Service Endpoints *
     * ----------------------------- *
     *    Room Service Endpoints     *
     *                               */

    @Override
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking){
        return searchService.searchRooms(arrival, departure, numberOfBeds, typeOfBeds, smoking);
    }

    @Override
    public List<Room> getRooms(){
        return roomDAO.getAllRooms();
    }

    /**                              *
     * End of Room Service Endpoints *
     * ----------------------------- *
     * Billing Service Endpoints *
     *                               */

    public List<Invoice> generateBillingReport(String username){

        return reportService.getInvoiceList(username);
    }

    /**                              *
     * End of Room Service Endpoints *
     * ----------------------------- *
     * Reservation Service Endpoints *
     *                               */
    @Override
    public List<Reservation> getReservationByUserId(UUID id){
        return reservationDAO.findReservations(id);
    }

    @Override
    public Reservation getReservationByReservationId(UUID id) {
        return reservationDAO.findReservation(id);
    }

//    @Override
//    public void addViewsHandler(GuiHandler guiHandler){
//        this.guiHandler = guiHandler;
//    }

    @Override
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        return reservationDAO.createReservation(guest, room, arrival, departure);
    }

    public User getUser(String username) {
        return getUserDAO().getUserByUsername(username);
    }


    @Override
    public Response cancelReservation(Reservation reservation) {
        return reservationDAO.cancelReservation(reservation);
    }

    @Override
    public Response modifyReservation(Reservation modifiedReservation) {
        return reservationDAO.updateReservation(modifiedReservation);
    }

    @Override
    public Response checkIn(Reservation reservation) {
        return reservationService.checkIn(reservation);
    }

    @Override
    public Response checkOut(Reservation reservation) {
        return reservationService.checkOut(reservation);
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
//        if(getSessionDAO().validateSession(sessionId).equals("Admin")){
//            // Generate the List.
//            // Update the Report Page with details.
//        };
    }

//    @Override
//    public void getInvoice(Reservation reservation) {
//        // Get the invoice a reservation.
//        getReservationDAO().getInvoice(reservation);
//    }

    @Override
    public void payInvoice(Reservation reservation) {

    }

    @Override
    public void addViewsHandler(Frame guiHandler) {
        this.guiHandler = guiHandler;
    }

    /**
     * ROOM ROUTES BEGIN
     */
    @Override
    public void createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
        roomDAO.createRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }

    @Override
    public Room getRoom(int roomId){
        return roomDAO.getRoom(roomId);
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





    @Override
    public void getInvoice(Reservation reservation) {

    }





    /**
     * APP CONTROLLER PRIVATE ACCESS METHODS
     */
    private Frame getGuiHandler() {
        return guiHandler;
    }

    private void setGuiController(Frame guiHandler) {
        this.guiHandler = guiHandler;
    }

    private SessionDao getSessionDAO() {
        return sessionDAO;
    }

    private void setSessionDAO(SessionDao sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    private UserDao getUserDAO() {
        return userDAO;
    }

    private void setUserDAO(UserDao userDAO) {
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
