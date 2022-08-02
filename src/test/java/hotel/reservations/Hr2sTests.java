package hotel.reservations;

import hotel.reservations.controller.ApplicationController;
import hotel.reservations.controller.PrimaryController;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.*;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.*;
import hotel.reservations.services.ReservationDAO.ReservationDAO;
import hotel.reservations.services.RoomDAO.RoomDAO;
import hotel.reservations.services.UserDAO.UserDAO;
import hotel.reservations.services.ReservationDAO.ReservationDAO;
import hotel.reservations.services.authentication.HotelAuth;
import hotel.reservations.views.controller.GuiFrame;
import hotel.reservations.views.controller.GuiHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        roomDAO = new RoomDAO(db);
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
        User user = db.getUser("admin");
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
        User user = db.getUser("admin");
        assertTrue(user.getAccountType() == Account.ADMIN);
        assertTrue(null != ud.createDefaultUser(Account.CLERK, "clerk1", "Clerky", "McClerk", "1234 Clerk Street", "ClerkState", "24680"));
    }

    @Test
    @Order(5)
    void getClerkUser() {
        User user = db.getUser("clerk1");
        assertTrue(user instanceof Clerk);
    }

    @Test
    @Order(6)
    void getAvailableRooms() {
        ArrayList<Room> rooms = roomDAO.filterRooms(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-12-01"));
        assertTrue(rooms.size() > 0);
    }

    @Test
    @Order(7)
    void UC01() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123$";
        LocalDate arrival = LocalDate.parse("2022-09-01");
        LocalDate departure = LocalDate.parse("2022-09-15");

        // find available rooms
        ArrayList<Room> rooms = roomDAO.filterRooms(arrival, departure);
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
        User user = db.getUser("uc01_guest");

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

    @Test
    @Order(8)
    void modifyProfile() {
        User user1 = db.getUser("clerk1");
        User user2 = db.getUser("clerk1");

        ud.updateUser(user2.getUserId(), "newClerk", "Clerky", "McClerk", "123 Main St", "CA", "12345", true);
        assertNotSame(user1.getUsername(), user2.getUsername());
    }

    @Test
    @Order(9)
    void resetPassword() {

        ud.createUser(Account.GUEST, "guest1", "password", "John", "Smith", "123 Main", "VA", "123456");
        User user1 = db.getUser("guest1");
        String str = "password123";
        String strOld = "password";
        ud.changePassword("guest1", strOld.toCharArray(),str.toCharArray());

        assertNotNull(ud.logIn("user1", str.toCharArray()));
    }



}