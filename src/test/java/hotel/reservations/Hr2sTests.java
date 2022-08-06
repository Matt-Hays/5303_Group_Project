package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.controller.AppControllerImpl;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.*;
import hotel.reservations.persistence.DatabaseImpl;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.persistence.dao.impls.ReservationDaoImpl;
import hotel.reservations.persistence.dao.impls.RoomDaoImpl;
import hotel.reservations.persistence.dao.impls.UserDaoImpl;
import hotel.reservations.views.frame.FrameImpl;
import hotel.reservations.views.frame.Frame;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        // Associate Views with the ApplicationController
        appController.addViewsHandler(guiHandler);
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
        User user = appController.getUser("admin");
        System.out.println(user);
        assertTrue(null != user);
        if (null != user) {
            assertTrue(user.getAccountType() == Account.ADMIN);
        }
    }

    /**
     * test registration of a user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(3)
    void registerUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        assertTrue(null != appController.registerUser("guest1", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345"));
    }

    /**
     * test creation of a clerk
     */
    @Test
    @Order(4)
    void createClerk() {
        appController.createClerk("clerk1", "Clerky", "McClerk", "1234 Clerk Street", "ClerkState", "24680");
        User user = appController.getUser("clerk1");
        assertTrue(user.getAccountType() == Account.CLERK);
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
        User user = appController.getUser(username);
        assertTrue(null != user);

        Session session = appController.logIn(user.getUsername(), password.toCharArray());
        user.setLastName(newLastName);
        appController.modifyUser(session.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getStreet(), user.getState(), user.getZipCode(), user.getActive());

        user = appController.getUser(username);
        assertTrue(user.getLastName().equals(newLastName));

        appController.logOut(session.getId());
    }

    /**
     * test getting available rooms
     */
    @Test
    @Order(6)
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
    @Order(7)
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
        List<Reservation> reservations = appController.getReservationByUserId(user.getUserId());
        Boolean found = false;
        for(Reservation r: reservations) {
            if (0 == reservation.getReservationId().toString().compareTo(r.getReservationId().toString())) {
                found = true;
                break;
            }
        }

        assertTrue(found);

        // TODO: how can I check success
        appController.logOut(sessionId);
    }

    /**
     * test checking in and checking out
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(8)
    void checkInOut() throws NoSuchAlgorithmException, InvalidKeySpecException  {
        String password = "password123$";

        // act like they've logged in
        Session session = appController.logIn("uc01_guest", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        User user = session.getUser();
        assertTrue(null != user);

        // get reservations
        List<Reservation> reservations = appController.getReservationByUserId(user.getUserId());
        assertFalse(reservations.isEmpty());

        // choose the first reservation
        Reservation reservation = reservations.get(0);
        assertTrue(null != reservation);

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

        appController.logOut(sessionId);
    }

    /**
     * test canceling a reservation
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @Order(9)
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

        // checkIn
        Response response = appController.cancelReservation(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates checkedIn
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.CANCELLED);

        appController.logOut(sessionId);
    }

    /**
     * test modifying a reservation
     */
    @Test
    @Order(10)
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

        reservation.setCheckout(newDeparture);
        Response response = appController.modifyReservation(reservation);
        assertTrue(response == Response.SUCCESS);

        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getDeparture().isEqual(newDeparture));

        appController.logOut(sessionId);
    }

    /**
     * test retrieving a room
     */
    @Test
    @Order(11)
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

        appController.logOut(sessionId);
    }

    /**
     * test creating a new room
     */
    @Test
    @Order(12)
    void insertRoom() {
        String password = "password123$";
        int roomNumber = 1024;

        // act like they've logged in
        Session session = appController.logIn("clerk1", password.toCharArray());
        UUID sessionId = session.getId();
        assertTrue(null != sessionId);

        Response response = appController.createRoom(roomNumber, Bed.KING, 2, false, false, 1999.99);
        assertTrue(response == Response.SUCCESS);

        // try to insert duplicate roomId
        response = appController.createRoom(roomNumber, Bed.QUEEN, 4, false, false, 195.35);
        assertTrue(response == Response.FAILURE);

        Room room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);

        appController.logOut(sessionId);
    }

    /**
     * test modification of a room
     */
    @Test
    @Order(13)
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

        room = appController.getRoom(roomNumber);
        assertTrue(null != room);
        assertTrue(room.getRoomId() == roomNumber);
        assertTrue(room.getNumBeds() == numBeds);

        appController.logOut(sessionId);
    }

    /**
     * test deleting a room
     */
    @Test
    @Order(14)
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

        appController.logOut(sessionId);
    }
}