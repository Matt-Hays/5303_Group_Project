package hotel.reservations;

import hotel.reservations.controller.ApplicationController;
import hotel.reservations.controller.PrimaryController;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.*;
import hotel.reservations.persistence.Database;
import hotel.reservations.persistence.daos.reservationDAO.ReservationDAO;
import hotel.reservations.persistence.daos.roomDAO.RoomDAO;
import hotel.reservations.persistence.daos.UserDAO.UserDAO;
import hotel.reservations.views.controller.GuiFrame;
import hotel.reservations.views.controller.GuiHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class Hr2sTests {
    private static final String dbName = "hr2s_test.sqlite";
    private static Database db = null;
    private static ApplicationController applicationController = null;
    private static UserDAO ud = null;
    private static ReservationDAO reservationDAO = null;
    private static RoomDAO roomDAO = null;

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
        db = new Database(dbName);

        applicationController = new PrimaryController(db);
        GuiHandler guiHandler = new GuiFrame(applicationController);
        // Associate Views with the ApplicationController
        applicationController.addViewsHandler(guiHandler);

        ud = new UserDAO(db);
        reservationDAO = new ReservationDAO(db);
        roomDAO = new RoomDAO(db, reservationDAO);
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
        applicationController.registerUser("uc01_guest", password.toCharArray(), "bob", "thebuilder",
                "1234 Something Street", "MyState", "12345");

        // act like they've logged in
        applicationController.logIn("uc01_guest", password.toCharArray());

        // I don't have access to the private sessionDAO to be able to validate login
        User user = ud.getUserByUsername("uc01_guest");

        // create a reservation
        applicationController.createReservation(user, rooms.get(0), arrival, departure);

        // check the db to see if the reservation was successful
        ArrayList<Reservation> reservations = reservationDAO.findReservations(user.getUserId());
        Boolean found = false;
        for(Reservation reservation: reservations) {
            if (reservation.getArrival().isEqual(arrival) && reservation.getDeparture().isEqual(departure)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

    
}