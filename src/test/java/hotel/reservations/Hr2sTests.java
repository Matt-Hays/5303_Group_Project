package hotel.reservations;

import hotel.reservations.persistence.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class Hr2sTests {
    private final String dbName = "testDB.sqlite";
    Database db = null;

    /**
     * Ensures database is created and in a clean state before each test
     */
    @BeforeEach
    void setup() {
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
    @AfterEach
    void teardown() {
        // close the existing database
        if (null != db) {
            db.close();
        }

        File dbFile = new File(dbName);
        // delete the current database if it exists
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Test
    void testDatabaseReady() {
        assertTrue(db.ready());
    }
}