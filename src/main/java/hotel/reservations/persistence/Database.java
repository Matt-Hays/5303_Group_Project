package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.*;
import hotel.reservations.services.Response;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Database implements IDatabase {

	private String dbName = null;
	private Connection conn = null;
	private Logger logger;

	/**
	 * Default constructor
	 */
	public Database() {
		this("hr2s.sqlite");
	}

	/**
	 * Constructor that allows for spcificaton of database name
	 * @param dbName
	 */
	public Database(String dbName) {
		this.dbName = dbName;
		logger = Logger.getLogger(Database.class.getName());
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
			// Query to pull all room information from db
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
	public Room getRoom(int roomId) {
		// Build the query
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT `bedType`, `numBeds`, `nightlyRate`, `smoking`, `occupied` FROM `room` WHERE `id`=?;"
			);
			ps.setInt(1, roomId);

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for room: " + roomId);
				return null;
			}

			Bed bedType = Bed.valueOf(rs.getString("bedType"));
			int numBeds = rs.getInt("numBeds");
			Boolean smoking = rs.getBoolean("smoking");
			Boolean occupied = rs.getBoolean("occupied");
			double nightlyRate = rs.getDouble("nightlyRate");

			return new Room(roomId, bedType, numBeds, smoking, occupied, nightlyRate);
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * Updates a room
	 * @param room a room
	 * @return Response - Success or Fail
	 */
	public Response updateRoom (Room room) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `room` " +
					"SET `bedType`=?, `numBeds`=?, `smoking`=?, `occupied`=?, `nightlyRate` =? " +
					"WHERE id = ?");
			ps.setString(1, room.getBedType().name());
			ps.setInt(2, room.getNumBeds());
			ps.setBoolean(3, room.getSmoking());
			ps.setBoolean(4, room.getOccupied());
			ps.setDouble(5, room.getNightlyRate());
			ps.setInt(6, room.getRoomId());

			// Execute the query
			if (ps.executeUpdate() > 0) {
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
	public Reservation getReservation(UUID reservationId) {
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

			UUID customerId = UUID.fromString(rs.getString("customerId"));
			UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
			int roomId = rs.getInt("roomId");
			LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
			LocalDate arrival = LocalDate.parse(rs.getString("arrival"));
			LocalDate departure = LocalDate.parse(rs.getString("departure"));
			ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

			return new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status);

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
	public ArrayList<Reservation> getReservationByGuestId(UUID customerId) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
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
				return reservations;
			}

			while (rs.next()) {
				UUID reservationId = UUID.fromString(rs.getString("id"));
				UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
				int roomId = rs.getInt("roomId");
				LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
				LocalDate arrival = LocalDate.parse(rs.getString("arrival"));
				LocalDate departure = LocalDate.parse(rs.getString("departure"));
				ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

				reservations.add(new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status));
			}

			return reservations;

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return reservations;
	}

	/**
	 * Inserts a new reservation into the Database
	 * @param r
	 * @return Response - Success or Fail
	 */
	public Response insertReservation(Reservation r) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `reservation` " +
							"(`id`, `customerId`, `invoiceId`, `roomId`, `createdAt`, `arrival`, `departure`, `status`) " +
							"VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, r.getReservationId().toString());
			ps.setString(2, r.getCustomerId().toString());
			ps.setString(3, r.getInvoiceId().toString());
			ps.setInt(4, r.getRoomNumber());
			ps.setString(5, r.getCreatedAt().toString());
			ps.setString(6, r.getArrival().toString());
			ps.setString(7, r.getDeparture().toString());
			ps.setString(8, r.getStatus().name());

			// Execute the query
			if (ps.executeUpdate() > 0) {
				return Response.FAILURE;
			}

			return Response.SUCCESS;

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
			ps.executeUpdate();
			return Response.SUCCESS;

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
			if (ps.executeUpdate() > 0) {
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
	 * @param r a reservation object
	 */
	public Response updateReservation(Reservation r) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE `reservation` " +
					"SET `customerId`=?, `invoiceId`=?, `roomId`=?, `createdAt`=?, `arrival`=?, `departure`=?, `status`=? " +
					"WHERE `id`=?;");
			ps.setString(1, r.getCustomerId().toString());
			ps.setString(2, r.getInvoiceId().toString());
			ps.setInt(3, r.getRoomNumber());
			ps.setString(4, r.getCreatedAt().toString());
			ps.setString(5, r.getArrival().toString());
			ps.setString(6, r.getDeparture().toString());
			ps.setString(7, r.getStatus().name());
			ps.setString(8, r.getReservationId().toString());

			// Execute the update
			if (ps.executeUpdate() > 0) {
				return Response.SUCCESS;
			};

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	/**
	 * Delete a reservation from the database (invoice table and reservation table)
	 * @param r the reservation id
	 * @return success or fail
	 */
	public Response deleteReservation(Reservation r) {
		try {
			// invoices for the reservation
			PreparedStatement ps = conn.prepareStatement("DELETE FROM `invoice` " +
					"WHERE `id`=?;");

			ps.setString(1, r.getInvoiceId().toString());

			// Execute the query
			ps.executeQuery();

			// remove the reservation
			ps = conn.prepareStatement("DELETE FROM `reservation` " +
					"WHERE `id`=?;");

			ps.setString(1, r.getReservationId().toString());

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
	public ArrayList<Reservation> getOverlappingReservations(LocalDate arrival, LocalDate departure) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

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
				return reservations;
			}

			while (rs.next()) {
				UUID reservationId = UUID.fromString(rs.getString("id"));
				UUID customerId = UUID.fromString(rs.getString("customerId"));
				UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
				int roomId = rs.getInt("roomId");
				LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
				ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

				reservations.add(new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status));
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return reservations;
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
			if (ps.executeUpdate() > 0) {
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
			if (ps.executeUpdate() > 0) {
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
	 * Returns a list of rooms
	 */
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> allRooms = new ArrayList<Room>();

		try {
			// Query to pull all room information from db
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM `room`");
			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return allRooms;
			}

			while (rs.next()) {
				int roomId = rs.getInt("id");
				boolean smoking = rs.getBoolean("smoking");
				int numBeds = rs.getInt("numBeds");
				Bed bedType = Bed.valueOf(rs.getString("bedType"));
				boolean occupied = rs.getBoolean("occupied");
				double nightly_rate = rs.getDouble("nightlyRate");

				allRooms.add(new Room(roomId, bedType, numBeds, smoking, occupied, nightly_rate));
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return allRooms;
	}

}