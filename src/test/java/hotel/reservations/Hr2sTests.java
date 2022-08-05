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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class Hr2sTests {
    private static final String dbName = "hr2s_test.sqlite";
    private static DatabaseImpl db = null;
    private static AppController appController = null;
    private static UserDaoImpl ud = null;
    private static ReservationDaoImpl reservationDAO = null;
    private static RoomDaoImpl roomDAO = null;

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

        ud = new UserDaoImpl(db);
        reservationDAO = new ReservationDaoImpl(db);
        roomDAO = new RoomDaoImpl(db, reservationDAO);
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
        User user = ud.getUserByUsername("admin");
        System.out.println(user);
        assertTrue(null != user);
        if (null != user) {
            assertTrue(user.getAccountType() == Account.ADMIN);
        }
    }

    @Test
    @Order(3)
    void registerUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        assertTrue(null != ud.createUser(Account.GUEST, "guest1", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345"));
    }


    /**
     * creation of a clerk
     */
    @Test
    @Order(4)
    void createClerk() {
        User user = ud.getUserByUsername("admin");
        assertTrue(user.getAccountType() == Account.ADMIN);
        assertTrue(null != ud.createDefaultUser(Account.CLERK, "clerk1", "Clerky", "McClerk", "1234 Clerk Street", "ClerkState", "24680"));
    }

    @Test
    @Order(5)
    void getClerkUser() {
        User user = ud.getUserByUsername("clerk1");
        assertTrue(user instanceof Clerk);
    }

    @Test
    @Order(6)
    void getAvailableRooms() {
        LocalDate arrival = LocalDate.parse("2022-01-01");
        LocalDate departure = LocalDate.parse("2022-12-01");
        ArrayList<Room> rooms = roomDAO.filterRooms(arrival, departure, Bed.KING, 1, false);
        assertTrue(rooms.size() > 0);
    }

    @Test
    @Order(7)
    void UC01() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-09-01");
        LocalDate departure = LocalDate.parse("2022-09-15");

        // find available rooms
        ArrayList<Room> rooms = roomDAO.filterRooms(arrival, departure, Bed.QUEEN, 2, false);
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

        // TODO: how can I check sucess
        appController.logOut(sessionId);
    }

    @Test
    @Order(8)
    void checkin() throws NoSuchAlgorithmException, InvalidKeySpecException  {
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

        // checkin
        Response response = appController.checkIn(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates checkedin
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.CHECKEDIN);

        // checkout
        response = appController.checkOut(reservation);
        assertTrue(response == Response.SUCCESS);

        // check if db indicates complete
        reservation = appController.getReservationByReservationId(reservation.getReservationId());
        assertTrue(reservation.getStatus() == ReservationStatus.COMPLETE);
    }
}