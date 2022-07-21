package hotel.reservations;

import hotel.reservations.models.user.Account;
import hotel.reservations.models.user.Admin;
import hotel.reservations.models.user.Clerk;
import hotel.reservations.models.user.Guest;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.*;
import hotel.reservations.services.authentication.HotelAuth;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class Hr2sTests {
    private static final String dbName = "testDB.sqlite";
    private static Database db = null;

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
        db = Database.testDatabase(dbName);
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
        Object obj = db.getUser("admin");
        assertTrue(obj instanceof Admin);
    }

    @Test
    @Order(3)
    void registerUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserCatalog uc = UserCatalog.getUserCatalog();
        uc.createNewGuest(Account.GUEST, "guest1", HotelAuth.generatePasswordHash("password123$"), "bob", "thebuilder", true);
    }

    /**
     * test retrieval of a guest user
     */
    @Test
    @Order(4)
    void getGuestUser() {
        Object obj = db.getUser("guest1");
        assertTrue(obj instanceof Guest);
    }

    /**
     * creation of a clerk
     */
    @Test
    @Order(5)
    void createClerk() {
        Object obj = db.getUser("admin");
        assertTrue(obj instanceof Admin);

        Admin admin = (Admin)obj;
        assertEquals(Response.SUCCESS, admin.createUser(Account.CLERK, "clerk1", "Clerky", "McClerk"));
    }
}