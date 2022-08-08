package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.controller.AppControllerImpl;
import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.persistence.Response;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.frame.FrameImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class Hr2sTests {
    private static final String dbName = "hr2s_test.sqlite";
    private static DatabaseImpl db = null;
    private static AppController appController = null;

    /**
     * Ensures database is created and in a clean state before each test
     */
    @BeforeAll
    static void setup() {
        File dbFile = new File(dbName);
        // delete the current database if it exists
        if (dbFile.exists()) {
            dbFile.delete();
        }
        // create the test db
        db = new DatabaseImpl(dbName);

        appController = new AppControllerImpl(db);
        Frame guiHandler = new FrameImpl(appController);
    }

    /**
     * Ensures the database does not persist between tests
     */
    @AfterAll
    static void teardown() {
        File dbFile = new File(dbName);
        // delete the current database if it exists
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    /**
     * test whether the database is ready
     */
    @Test
    @Order(1)
    void testDatabaseReady() {
        assertTrue(db.ready());
    }

    /**
     * test retrieval of a user
     */
    @Test
    @Order(2)
    void getAdminUser() {
        String password = "password123$";

        // log the admin user in
        Session session = appController.logIn("admin", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);
        assertTrue(user.getAccountType() == Account.ADMIN);

        Response response = appController.logOut(sessionId);
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test registration of a user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(3)
    void registerUser() {
        String password = "password123$";

        // register a guest
        Session session = appController.registerUser("guest1", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345");

        assertTrue(null != session);
        User user = session.getUser();
        assertTrue(user.getAccountType() == Account.GUEST);

        // attempt to register the same username again
        Session nullSession = appController.registerUser("guest1", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345");
        assertTrue(null == nullSession);

        Response response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test creation of a clerk
     */
    @Test
    @Order(4)
    void createClerk() {
        String password = "password123$";

        // create a clerk
        Response response = appController.createClerk("clerk1", "Clerky", "McClerk", "1234 Clerk Street", "ClerkState", "24680");
        assertTrue(response == Response.SUCCESS);
        // attempt to register same username
        response = appController.createClerk("clerk1", "Clerky", "McClerk", "1234 Clerk Street", "ClerkState", "24680");
        assertTrue(response == Response.FAILURE);

        // log the clerk user in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);
        assertTrue(user.getAccountType() == Account.CLERK);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test modification of a user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(5)
    void modifyUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        String newLastName = "MyNewLastName";
        String username = "clerk1";

        // log the clerk user in
        Session session = appController.logIn(username, password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);
        User user = session.getUser();
        assertTrue(null != user);

        user.setLastName(newLastName);
        user = appController.modifyUser(session.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getStreet(), user.getState(), user.getZipCode(), user.getActive());

        assertTrue(user.getLastName().equals(newLastName));

        Response response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    @Test
    @Order(6)
    void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String currentPassword = "password123$";
        String oldPassword = currentPassword;
        String newPassword = "ALongerPassword123$";
        String username = "guest1";

        // log the clerk user in
        Session session = appController.logIn(username, currentPassword.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);
        User user = session.getUser();
        assertTrue(null != user);

        Response response = appController.resetPassword(username, currentPassword.toCharArray(), newPassword.toCharArray());
        assertTrue(response == Response.SUCCESS);

        // attempt to change password using the old password
        response = appController.resetPassword(username, oldPassword.toCharArray(), newPassword.toCharArray());
        assertTrue(response == Response.FAILURE);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test getting available rooms
     */
    @Test
    @Order(7)
    void getAvailableRooms() {
        LocalDate arrival = LocalDate.parse("2022-01-01");
        LocalDate departure = LocalDate.parse("2022-12-01");
        List<Room> rooms = appController.searchRooms(arrival, departure, 2, Bed.QUEEN, true);
        assertTrue(rooms.size() > 0);
    }

    /**
     * more comprehensive test covering UC01 - Make a reservation
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(8)
    void UC01() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-09-01");
        LocalDate departure = LocalDate.parse("2022-09-15");

        // find available rooms
        List<Room> rooms = appController.searchRooms(arrival, departure, 2, Bed.QUEEN, false);
        if (rooms.size() == 0) {
            Assertions.fail("No rooms found");
            return;
        }

        // register a new user
        appController.registerUser("uc01_guest", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345");

        // act like they've logged in
        Session session = appController.logIn("uc01_guest", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        // create a reservation
        User user = session.getUser();
        assertTrue(null != user);
        Reservation reservation = appController.createReservation(user, rooms.get(0), arrival, departure);
        assertTrue(null != reservation);

        // check the db to see if the reservation was successful
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(null != reservation);

        Response response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test checking in and checking out
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(9)
    void checkInOut() throws NoSuchAlgorithmException, InvalidKeySpecException  {
        String password = "password123$";

        // act like they've logged in
        Session session = appController.logIn("uc01_guest", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);

        // get reservations
        List<Reservation> reservations = appController.getReservationByUsername(user.getUsername());
        assertTrue(reservations.size() > 0);

        // choose the first reservation
        Reservation reservation = reservations.get(0);
        assertTrue(null != reservation);
        assertTrue(ReservationStatus.AWAITING == reservation.getStatus());

        // checkIn
        Response response = appController.checkIn(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates checkedIn
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.CHECKEDIN);

        // checkout
        response = appController.checkOut(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates complete
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.COMPLETE);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test canceling a reservation
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(10)
    void cancelReservation() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-10-01");
        LocalDate departure = LocalDate.parse("2022-10-15");

        // find available rooms
        List<Room> rooms = appController.searchRooms(arrival, departure, 2, Bed.QUEEN, false);
        if (rooms.size() == 0) {
            Assertions.fail("No rooms found");
            return;
        }

        // act like they've logged in
        Session session = appController.logIn("uc01_guest", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);

        // create a reservation
        Reservation reservation = appController.createReservation(user, rooms.get(0), arrival, departure);
        assertTrue(null != reservation);

        // cancel reservation
        Response response = appController.cancelReservation(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates cancelled
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.CANCELLED);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test modifying a reservation
     */
    @Test
    @Order(11)
    void modifyReservation() {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-11-01");
        LocalDate departure = LocalDate.parse("2022-11-15");
        LocalDate newDeparture = LocalDate.parse("2022-11-18");

        // find available rooms
        List<Room> rooms = appController.searchRooms(arrival, departure, 2, Bed.QUEEN, false);
        if (rooms.size() == 0) {
            Assertions.fail("No rooms found");
            return;
        }

        // act like they've logged in
        Session session = appController.logIn("uc01_guest", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);

        // create a reservation
        Reservation reservation = appController.createReservation(user, rooms.get(0), arrival, departure);
        assertTrue(null != reservation);

        // modify reservation
        reservation.setCheckout(newDeparture);
        Response response = appController.modifyReservation(reservation);
        assertTrue(response == Response.SUCCESS);

        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getDeparture().isEqual(newDeparture));

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test retrieving a room
     */
    @Test
    @Order(12)
    void getRoom() {
        String password = "password123$";
        int roomNumber = 102;

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        Room room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);

        Response response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test creating a new room
     */
    @Test
    @Order(13)
    void insertRoom() {
        String password = "password123$";
        int roomNumber = 1024;

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        // create a room
        Response response = appController.createRoom(roomNumber, Bed.KING, 2, false, false, 1999.99);
        assertTrue(response == Response.SUCCESS);

        // try to insert duplicate roomId
        response = appController.createRoom(roomNumber, Bed.QUEEN, 4, false, false, 195.35);
        assertTrue(response == Response.FAILURE);

        // ensure new room is in the db
        Room room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test modification of a room
     */
    @Test
    @Order(14)
    void modifyRoom() {
        String password = "password123$";
        int roomNumber = 102;
        int numBeds = 1500;

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        // get a room to modify
        Room room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);

        // this room is now a barracks
        room.setNumBeds(numBeds);

        // modify a room
        Response response = appController.updateRoom(room.getRoomId(), room.getBedType(), room.getNumBeds(), room.getSmoking(), room.getOccupied(), room.getNumBeds());
        assertTrue(response == Response.SUCCESS);

        // ensure room was updated in db
        room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);
        assertTrue(room.getNumBeds() == numBeds);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test deleting a room
     */
    @Test
    @Order(15)
    void deleteRoom() {
        String password = "password123$";
        int roomNumber = 1024;

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        // delete the room
        Response response = appController.deleteRoom(roomNumber);
        assertTrue(response == Response.SUCCESS);

        // try to delete it again
        response = appController.deleteRoom(roomNumber);
        assertTrue(response == Response.FAILURE);

        // make sure it is not in the db
        Room room = appController.getRoom(roomNumber);
        assertTrue(null == room);

        response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    /**
     * test generating an invoice
     */
    @Test
    @Order(16)
    void generateInvoice() {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-10-01");
        LocalDate departure = LocalDate.parse("2022-10-15");

        // find available rooms
        List<Room> rooms = appController.searchRooms(arrival, departure, 2, Bed.QUEEN, false);
        //registers a guest and creates a reservation to trigger the creation of an invoice for that reservation
        Session testSession = appController.registerUser("guest3", password.toCharArray(), "FirstName", "LastName", "123 Main St", "California", "12345");
        User guest3 = testSession.getUser();
        appController.createReservation(guest3, rooms.get(0), arrival, departure);

        //testing the function to ensure it returns a report of invoices greater than size 0
        List<Invoice> invoiceList = appController.generateBillingReport(guest3.getUsername());

        assertTrue(invoiceList.size() > 0);
    }

    @Test
    @Order(17)
    void viewBillingReport(){

        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-06-01");
        LocalDate departure = LocalDate.parse("2022-06-30");

        List<Room> rooms = appController.searchRooms(arrival, departure, 1, Bed.KING, false);
        if (rooms.size() == 0) {
            Assertions.fail("No rooms found");
            return;
        }

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);

        // create a reservation
        Reservation reservation = appController.createReservation(user, rooms.get(0), arrival, departure);
        assertTrue(null != reservation);

        // get invoiceId from reservation
        UUID invoiceId = reservation.getInvoiceId();

        // generate an invoice for the created reservation
        Invoice invoice = appController.getInvoice(invoiceId);
        assertTrue(null != invoice);

        Response response = appController.logOut(session.getId());
        assertTrue(response == Response.SUCCESS);
    }

    @Test
    @Order(18)
    void resetPassword(){
        String username = "guest2";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        //registering a new user to reset their password
        Session session = appController.registerUser(username, oldPassword.toCharArray(), "Test", "Guest", "123 Main St.", "California", "12345");
        assertTrue(null != session);

        //reset password
        assertSame(Response.SUCCESS, appController.resetPassword(username, oldPassword.toCharArray(), newPassword.toCharArray()));
    }
}