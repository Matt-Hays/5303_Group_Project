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
import hotel.reservations.persistence.dao.ReservationDao;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.persistence.dao.SessionDao;
import hotel.reservations.persistence.dao.UserDao;
import hotel.reservations.persistence.dao.impls.ReservationDaoImpl;
import hotel.reservations.persistence.dao.impls.RoomDaoImpl;
import hotel.reservations.persistence.dao.impls.SessionDaoImpl;
import hotel.reservations.persistence.dao.impls.UserDaoImpl;
import hotel.reservations.services.ReportService;
import hotel.reservations.services.ReservationService;
import hotel.reservations.services.RoomService;
import hotel.reservations.services.UserService;
import hotel.reservations.services.impls.ReportServiceImpl;
import hotel.reservations.services.impls.ReservationServiceImpl;
import hotel.reservations.services.impls.RoomServiceImpl;
import hotel.reservations.services.impls.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppControllerImpl implements AppController{
    private final UserService userService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final ReportService reportService;


    public AppControllerImpl(DatabaseImpl db){
        SessionDao sessionDao = new SessionDaoImpl();
        UserDao userDao = new UserDaoImpl(db);
        ReservationDao reservationDao = new ReservationDaoImpl(db);
        RoomDao roomDao = new RoomDaoImpl(db, reservationDao);

        /**
         * Services
         */
        this.userService = new UserServiceImpl(userDao, sessionDao);
        this.roomService = new RoomServiceImpl(roomDao);
        this.reservationService = new ReservationServiceImpl(reservationDao, userDao);
        this.reportService = new ReportServiceImpl(reservationDao, userDao);
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
    public Response logOut(UUID id){
        return userService.logout(id);
    }

    @Override
    public Response resetPassword(String username, char[] oldPassword, char[] newPassword) {
        return userService.updatePassword(username, oldPassword, newPassword);
    }

    @Override
    public User modifyUser(UUID sessionId, String newUsername, String firstName, String lastName, String street, String state,
                           String zipCode, boolean active) {
        return userService.updateUser(sessionId, newUsername, firstName, lastName, street, state, zipCode, active);
    }

    @Override
    public Response createClerk(String username, String firstName, String lastName, String street, String state,
                            String zipCode) {
        return userService.createClerk(username, firstName, lastName, street, state, zipCode);
    }

    @Override
    public User getUser(String username) {
        return userService.getUserByUsername(username);
    }

    @Override
    public Response resetGuestPassword(UUID sessionId, String username){
        return userService.resetGuestPassword(sessionId, username);
    }

    /**                              *
     * End of User Service Endpoints *
     * ----------------------------- *
     *    Room Service Endpoints     *
     *                               */

    @Override
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking){
        return roomService.searchRooms(arrival, departure, numberOfBeds, typeOfBeds, smoking);
    }

    @Override
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
        return roomService.createRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }

    @Override
    public Room getRoom(int roomId){
        return roomService.getRoom(roomId);
    }

    @Override
    public Response updateRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate){
        return roomService.updateRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }
    public Response deleteRoom(int roomId) {
        return roomService.deleteRoom(roomId);
    }

    /**
     * Move to Service Layer
     */
    @Override
    public List<Room> getRooms(){
        return roomService.getAllRooms();
    }

    /**                              *
     * End of Room Service Endpoints *
     * ----------------------------- *
     *   Invoice Service Endpoints   *
     *                               */

    public List<Invoice> generateBillingReport(String username){
        return reportService.getInvoiceList(username);
    }

    /**                                 *
     * End of Invoice Service Endpoints *
     * -------------------------------- *
     *  Reservation Service Endpoints   *
     *                                  */
    @Override
    public List<Reservation> getReservationByUsername(String username){
        return reservationService.findReservationByUsername(username);
    }

    @Override
    public Reservation getReservationByReservationId(UUID id) {
        return reservationService.findReservationByReservationId(id);
    }

    @Override
    public Reservation createReservation(User guest, Room room, LocalDate arrival, LocalDate departure) {
        return reservationService.createReservation(guest, room, arrival, departure);
    }

    @Override
    public Reservation clerkCreateReservation(String username, Room room, LocalDate arrival, LocalDate departure) {
        return reservationService.clerkCreateReservation(username, room, arrival, departure);
    }

    @Override
    public Response cancelReservation(Reservation reservation) {
        return reservationService.cancelReservation(reservation);
    }

    @Override
    public Response modifyReservation(Reservation modifiedReservation) {
        return reservationService.modifyReservation(modifiedReservation);
    }

    @Override
    public Response checkIn(Reservation reservation) {
        return reservationService.checkIn(reservation);
    }

    @Override
    public Response checkOut(Reservation reservation) {
        return reservationService.checkOut(reservation);
    }

    /**                                     *
     * End of Reservation Service Endpoints *
     * ------------------------------------ *
     *   Unimplemented Service Endpoints    *
     *                                      */

    @Override
    public Response payInvoice(Reservation reservation) {
        return Response.FAILURE;
    }
}
