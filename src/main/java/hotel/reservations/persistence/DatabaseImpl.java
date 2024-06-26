/**
 * @file DatabaseImpl.java
 * @author Joshua Wellman
 * @brief Database.java implementing object. Abstracts PreparedStatements necessary for
 *        DBMS communications. Maintains a connection to the physical DBMS.
 * @dependencies Underlying physical DBMS.
 */

package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.user.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DatabaseImpl implements Database {

	private String dbName = null;
	private Connection conn = null;
	private Logger logger;

	/**
	 * Default constructor
	 */
	public DatabaseImpl() {
		this("hr2s.sqlite");
	}

	/**
	 * Constructor that allows for spcificaton of database name
	 * @param dbName
	 */
	public DatabaseImpl(String dbName) {
		this.dbName = dbName;
		logger = Logger.getLogger(DatabaseImpl.class.getName());
		// log to file rather than console
		try {
			LogManager.getLogManager().reset();
			FileHandler handler = new FileHandler("hr2s.log");
			logger.addHandler(handler);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		connect();
	}

	/**
	 * Connect to the database if it exists
	 */
	private void connect() {
		File dbFile = new File(dbName);
		boolean exists = dbFile.exists();

		try {
			String url = "jdbc:sqlite:" + dbName;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			if (!exists) {
				logger.info("DB " + dbName + " does not exist.");
				dbInit();
			} else {
				logger.info("DB " + dbName + " exists.");
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

	/**
	 * close the database
	 * @return success or failure
	 */
	public boolean close() {
		try {
			if (conn != null) {
				logger.info("Closing the database.");
				conn.close();
			}
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * Checks to see if the database is ready
	 * @return true (ready) / false (not ready)
	 */
	public boolean ready() {
		try {
			return !conn.isClosed() && !conn.isReadOnly();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Initialize the database
	 * @return Response - Success or Fail
	 */
	private Response dbInit() {

		logger.info("Initializing the Database.");
		return createDatabase();
	}

	/**
	 * attempt to execute a sql statement in the db
	 * @param sqlStatement the statement to be executed
	 * @return the results of the execution or null for failure
	 */
	private ResultSet execute(String sqlStatement) {
		ResultSet rs = null;

		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			// this may be expected
//			logger.info(e.getMessage());
			return null;
		}

		return rs;
	}

	/**
	 * queries the db for a user
	 * @param username the username to lookup
	 * @return a ResultSet from the query
	 */
	public ResultSet getUser(String username) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `user` WHERE `username`=?;"
			);
			ps.setString(1, username.toLowerCase());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for username: " + username);
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Retrieves a specific user based on userId
	 * @param userId the user id
	 * @return a resultset from the query
	 */
	public ResultSet getUser(UUID userId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `user` WHERE `id`=?;"
			);
			ps.setString(1, userId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for userId: " + userId);
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Retrieves all user entries
	 * @return a list of users
	 */
	public ResultSet getAllUsers() {
		try {
			// Query to pull all user information from db
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `user`");
			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * queries the database for the room based on room number
	 * @param roomId the room number
	 * @return a Room instance or null
	 */
	public ResultSet getRoom(int roomId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `room` WHERE `id`=?;"
			);
			ps.setInt(1, roomId);

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (rs.next() != false) {
				return rs;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Updates a room
	 * @param roomId
	 * @param bedType
	 * @param numBeds
	 * @param smoking
	 * @param occupied
	 * @param nightly_rate
	 * @return
	 */
	public Response updateRoom (int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `room` " +
					"SET `bedType`=?, `numBeds`=?, `smoking`=?, `occupied`=?, `nightlyRate` =? " +
					"WHERE id = ?");
			ps.setString(1, bedType.name());
			ps.setInt(2, numBeds);
			ps.setBoolean(3, smoking);
			ps.setBoolean(4, occupied);
			ps.setDouble(5, nightly_rate);
			ps.setInt(6, roomId);

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			};
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return Response.FAILURE;
	}

	/**
	 * recall a reservation
	 * @param reservationId the reservation to look up
	 * @return a Reservation instance or null
	 */
	public ResultSet getReservationByReservationId(UUID reservationId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `reservation` WHERE `id`=?;"
			);
			ps.setString(1, reservationId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for reservation: " + reservationId);
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Query reservation by guest ID
	 * @param customerId the id of the guest
	 * @return list of reservations
	 */
	public ResultSet getReservationByGuestId(UUID customerId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `reservation` WHERE `customerId`=?;"
			);
			ps.setString(1, customerId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				// an empty set could be normal
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Inserts a reservation into the database
	 * @param reservationId
	 * @param customerId
	 * @param invoiceId
	 * @param roomId
	 * @param createdAt
	 * @param arrival
	 * @param departure
	 * @param status
	 * @return
	 */
	public Response insertReservation(UUID reservationId, UUID customerId, UUID invoiceId, int roomId,
			LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `reservation` " +
							"(`id`, `customerId`, `invoiceId`, `roomId`, `createdAt`, `arrival`, `departure`, `status`) " +
							"VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, reservationId.toString());
			ps.setString(2, customerId.toString());
			ps.setString(3, invoiceId.toString());
			ps.setInt(4, roomId);
			ps.setString(5, createdAt.toString());
			ps.setString(6, arrival.toString());
			ps.setString(7, departure.toString());
			ps.setString(8, status.name());

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		// failure
		return Response.FAILURE;
	}

	/**
	 * Inserts a new invoice into the database
	 * @param i an invoice
	 * @return Response - Success or Fail
	 */
	public Response insertInvoice(Invoice i) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `invoice` " +
					"(`id`, `taxRate`, `fees`, `subTotal`, `isPaid`) " +
					"VALUES (?,?,?,?,?);");
			ps.setString(1, i.getInvoiceId().toString());
			ps.setDouble(2, i.getTaxRate());
			ps.setDouble(3, i.getFees());
			ps.setDouble(4, i.getSubtotal());
			ps.setBoolean(5, i.getIsPaid());


			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	/**
	 * Insert a new room in the database
	 * @param roomId
	 * @param bedType
	 * @param numBeds
	 * @param smoking
	 * @param occupied
	 * @param nightly_rate
	 * @return Response - Success or Fail
	 */
	public Response insertRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `room` " +
					"(`id`, `bedType`, `numBeds`, `smoking`, `occupied`, `nightlyRate`) " +
					"VALUES (?,?,?,?,?,?);");

			ps.setInt(1, roomId);
			ps.setString(2, bedType.name());
			ps.setInt(3, numBeds);
			ps.setBoolean(4, smoking);
			ps.setBoolean(5, occupied);
			ps.setDouble(6, nightly_rate);


			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	public Response deleteRoom(int roomId) {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM `room` WHERE `id`=?");

			ps.setInt(1, roomId);

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}
	/**
	 * Retrieves an invoice from the database
	 * @param invoiceId the ID of an invoice
	 * @return an invoice
	 */
	public Invoice getInvoice(UUID invoiceId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM `invoice` WHERE `id`=?;"
			);
			ps.setString(1, invoiceId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for reservation: " + invoiceId);
				return null;
			}

			double taxRate = rs.getDouble("taxRate");
			double fees = rs.getDouble("fees");
			double subTotal = rs.getDouble("subTotal");
			boolean isPaid = rs.getBoolean("isPaid");

			return new Invoice(invoiceId, taxRate, fees, subTotal, isPaid);

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Update an invoice
	 * @param i an invoice
	 * @return Response - Success or Fail
	 */
	public Response updateInvoice(Invoice i) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `invoice` " +
					"SET `taxRate`=?, `fees`=?, `subTotal`=?, `isPaid`=? " +
					"WHERE `id`=?;");
			ps.setDouble(1, i.getTaxRate());
			ps.setDouble(2, i.getFees());
			ps.setDouble(3, i.getSubtotal());
			ps.setBoolean(4, i.getIsPaid());
			ps.setString(5, i.getInvoiceId().toString());

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	/**
	 * Updates a reservation
	 * @param reservationId
	 * @param customerId
	 * @param invoiceId
	 * @param roomId
	 * @param createdAt
	 * @param arrival
	 * @param departure
	 * @param status
	 * @return
	 */
	public Response updateReservation(UUID reservationId, UUID customerId, UUID invoiceId, int roomId,
	LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `reservation` " +
					"SET `customerId`=?, `invoiceId`=?, `roomId`=?, `createdAt`=?, `arrival`=?, `departure`=?, `status`=? " +
					"WHERE `id`=?;");
				ps.setString(1, customerId.toString());
				ps.setString(2, invoiceId.toString());
				ps.setInt(3, roomId);
				ps.setString(4, createdAt.toString());
				ps.setString(5, arrival.toString());
				ps.setString(6, departure.toString());
				ps.setString(7, status.name());
				ps.setString(8, reservationId.toString());


			// Execute the update
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			};

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	/**
	 * Deletes a Reservation
	 * @param reservationId
	 * @param invoiceId
	 * @return
	 */
	public Response deleteReservation(UUID reservationId, UUID invoiceId) {
		try {
			// invoices for the reservation
			PreparedStatement ps = conn.prepareStatement("DELETE FROM `invoice` " +
					"WHERE `id`=?;");

			ps.setString(1, invoiceId.toString());

			// Execute the query
			ps.executeQuery();

			// remove the reservation
			ps = conn.prepareStatement("DELETE FROM `reservation` " +
					"WHERE `id`=?;");

			ps.setString(1, reservationId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set after deleting reservation");
				return Response.FAILURE;
			}
			return Response.SUCCESS;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}
	/**
	 * Retrieves a list of reservations that overlap with the requested arrival and departure dates
	 * @param arrival start date
	 * @param departure end date
	 * @return list of reservations
	 */
	public ResultSet getOverlappingReservations(LocalDate arrival, LocalDate departure) {

		try {
			// attempts to find reservations that overlap with requested arrival and departure dates
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `reservation` WHERE " +
					"(date(?) >= date(arrival) AND date(?) <  date(departure)) OR " +
					"(date(?) >  date(arrival) AND date(?) <= date(departure)) OR " +
					"(date(?) <= date(arrival) AND date(?) >= date(departure));");

			ps.setString(1, arrival.toString());
			ps.setString(2, arrival.toString());
			ps.setString(3, departure.toString());
			ps.setString(4, departure.toString());
			ps.setString(5, arrival.toString());
			ps.setString(6, departure.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				// an empty set could be normal
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * retrieves the password for a user from the database
	 * @param username the username to match on
	 * @return hashed password string or null
	 */
	public String getPassword(String username) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT `password` FROM `user` WHERE `username`=?;");
			ps.setString(1, username.toLowerCase());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for username: " + username);
				return null;
			}

			return rs.getString("password");

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * inserts a user into the database
	 * @param username the username
	 * @param hashed_password the prehashed/salted password
	 * @param fName first name
	 * @param lName lastname
	 * @param street street
	 * @param state state
	 * @param zipCode zip
	 * @return
	 */
	public Response insertUser(UUID userId, Account type, String username, String hashed_password,
							   String fName, String lName, String street, String state, String zipCode, Boolean active) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `user`" +
					" (`id`, `type`, `username`, `password`, `firstName`, `lastName`, `street`, `state`, `zip`, `active`) " +
					"values (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, String.valueOf(userId));
			ps.setString(2, type.name());
			ps.setString(3, username.toLowerCase());
			ps.setString(4, hashed_password);
			ps.setString(5, fName);
			ps.setString(6, lName);
			ps.setString(7, street);
			ps.setString(8, state);
			ps.setString(9, zipCode);
			ps.setBoolean(10, active);

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			};
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * updates all attributes of a user profile in the database
	 * @return
	 */
	public Response updateUserProfile(UUID userId, String newUsername, String firstName, String lastName,
									  String street, String state, String zipCode, boolean active){

		try {
			PreparedStatement ps = this.conn.prepareStatement("UPDATE `user` " +
					"SET `username`=?, `firstName`=?, `lastName`=?, `street` = ?, `state`=?, `zip`=?, `active`=? " +
					"WHERE `id` =?");

			ps.setString(1, newUsername);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, street);
			ps.setString(5, state);
			ps.setString(6, zipCode);
			ps.setBoolean(7, active);
			ps.setString(8, String.valueOf(userId));


			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			};
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * Updates a user
	 * @param userId
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param state
	 * @param zipCode
	 * @param active
	 * @return
	 */
	public Response updateUserProfile(UUID userId, String username, String firstName, String lastName, String street, String state, String zipCode, Boolean active) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `user` " +
					"SET `username`=?, `firstName`=?, `lastName`=?, `street`=?, `state`=?, `zipCode`=?, `active`=? " +
					"WHERE `id` =?");

			ps.setString(1, username.toLowerCase());
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, street);
			ps.setString(5, state);
			ps.setString(6, zipCode);
			ps.setBoolean(7, active);
			ps.setString(8, String.valueOf(userId));

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			};
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * updates the password for a user in the database
	 * @param username the username to match on
	 * @param newPasswordHash to validate the user
	 * @return
	 */
	public Response updatePassword(String username, String newPasswordHash) {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE `user` SET `password` = ? WHERE `username`=?;");
			ps.setString(1, newPasswordHash);
			ps.setString(2, username.toLowerCase());
			if (ps.executeUpdate() == 1) {
				return Response.SUCCESS;
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * Reads in the SQL statement to create the user table and passes it to executeQuery
	 * @return true (success) / false (fail)
	 */
	private Response createDatabase() {
		// read in resource files in this order
		String []sqlFiles = { "sql/user_tbl.sql", "sql/insert_users.sql", "sql/room_tbl.sql", "sql/insert_rooms.sql" , "sql/reservation_tbl.sql", "sql/invoice_tbl.sql"};

		for (String sqlFile : sqlFiles) {
			logger.info("Processing: " + sqlFile);
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream resource = classLoader.getResourceAsStream(sqlFile);
			if (resource == null) {
				logger.severe("Unable to locate resource: " + sqlFile);
				return Response.FAILURE;
			}

			String query;
			try {
				query = readFromInputStream(resource);
				resource.close();
			} catch (IOException e) {
				logger.severe("Unable to read from resource: " + sqlFile);
				return Response.FAILURE;
			}

			// execute and see if we got back a null object (indicates failure)
			execute(query);
		}

		return Response.SUCCESS;
	}

	/**
	 * deterines if the ResultSet is not null or closed
	 * @param rs the ResultSet to test
	 * @return true (valid), false (invalid)
	 */
	public boolean validate(ResultSet rs) {
		try {
			return rs != null && !rs.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * reads line by line through an InputStream building a single string along the way
	 * borrowed from <a href="https://www.baeldung.com/reading-file-in-java">baeldung.com</a>
	 * @param inputStream the stream to read from
	 * @return the completed string
	 * @throws IOException Unable to read from InputStream
	 */
	private static String readFromInputStream(InputStream inputStream)
			throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br
					 = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

	/**
	 * Gets all rooms form the database
	 * @return Resultset or NULL on error
	 */
	public ResultSet getAllRooms() {

		try {
			// Query to pull all room information from db
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `room`");
			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Find rooms matching criteria
	 * @param bedType
	 * @param numBeds
	 * @param smoking
	 * @return Resultset or NULL if error
	 */
	public ResultSet getFilteredRooms(Bed bedType, int numBeds, boolean smoking) {

		try {
			// Query to pull all room information from db
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `room` " +
				"WHERE `bedType`=? AND `numBeds`=? AND `smoking`=?");

				ps.setString(1, bedType.name());
			ps.setInt(2, numBeds);
			ps.setBoolean(3, smoking);

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return null;
			}

			return rs;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}
}