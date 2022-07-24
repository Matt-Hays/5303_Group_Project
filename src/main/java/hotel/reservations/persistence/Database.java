package hotel.reservations.persistence;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.reservation.ReservationStatus;
import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.models.user.*;
import hotel.reservations.services.authentication.HotelAuth;
import hotel.reservations.services.Response;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Database implements IDatabase {

	private String dbName = null;
	private Connection conn = null;
	private Logger logger;

	private static Database db = null;

	/**
	 * primary constructor
	 */
	public Database() {
		this("hr2s.sqlite");
	}

	/**
	 * test constructor for a test database that allows changing the db name
	 * @param dbName
	 */
	public Database(String dbName) {
		this.dbName = dbName;
		db.connect();
	}

	private void connect() {
		File dbFile = new File(dbName);
		boolean exists = dbFile.exists();

		try {
			String url = "jdbc:sqlite:" + dbName;
			// create a connection to the database
			db.conn = DriverManager.getConnection(url);

			if (!exists) {
				db.logger.info("DB " + dbName + " does not exist.");
				dbInit();
			} else {
				db.logger.info("DB " + dbName + " exists.");
			}
		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}
	}

	public boolean close() {
		try {
			if (db.conn != null) {
				db.logger.info("Closing the database.");
				db.conn.close();
			}
		} catch (SQLException ex) {
			db.logger.severe(ex.getMessage());
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
			return !db.conn.isClosed() && !db.conn.isReadOnly();
		} catch (SQLException e) {
			return false;
		}
	}

	private Response dbInit() {

		db.logger.info("Initializing the Database.");
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
			Statement statement = db.conn.createStatement();
			rs = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			// this may be expected
//			db.logger.info(e.getMessage());
			return null;
		}

		return rs;
	}

	/**
	 * queries the db for a user and returns a User instance if found
	 * @param username the username to lookup
	 * @return a User instance
	 */
	public User getUser(String username) {
		// Build the query
		try {
			PreparedStatement ps = db.conn.prepareStatement(
					"SELECT * FROM `user` WHERE `username`=?;"
			);
			ps.setString(1, username.toLowerCase());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for username: " + username);
				return null;
			}

			UUID userId = UUID.fromString(rs.getString("id"));
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String address = rs.getString("street");
			String state = rs.getString("state");
			String zipCode = rs.getString("zip");
			boolean active = rs.getBoolean("active");
			Account accountType = Account.valueOf(rs.getString("type"));

			switch (accountType) {
				case CLERK:
					return new Clerk(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
				case ADMIN:
					return new Admin(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
				case GUEST:
					return new Guest(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
			}

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> allUsers = new ArrayList<User>();

		try {
			// Query to pull all room information from db
			PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM `user`");
			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return allUsers;
			}

			do {
				UUID userId = UUID.fromString(rs.getString("id"));
				String username = rs.getString("username");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String address = rs.getString("street");
				String state = rs.getString("state");
				String zipCode = rs.getString("zip");
				boolean active = rs.getBoolean("active");
				Account accountType = Account.valueOf(rs.getString("type"));

				switch (accountType) {
					case CLERK:
						allUsers.add(new Clerk(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode));
						break;
					case ADMIN:
						allUsers.add(new Admin(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode));
						break;
					case GUEST:
						allUsers.add(new Guest(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode));
				}

			} while (rs.next());

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return allUsers;
	}

	public User getUser(UUID userId) {
		// Build the query
		try {
			PreparedStatement ps = db.conn.prepareStatement(
					"SELECT * FROM `user` WHERE `id`=?;"
			);
			ps.setString(1, userId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for userId: " + userId);
				return null;
			}
			String username = rs.getString("username");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String address = rs.getString("street");
			String state = rs.getString("state");
			String zipCode = rs.getString("zip");
			boolean active = rs.getBoolean("active");
			Account accountType = Account.valueOf(rs.getString("type"));

			switch (accountType) {
				case CLERK:
					return new Clerk(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
				case ADMIN:
					return new Admin(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
				case GUEST:
					return new Guest(userId, username.toLowerCase(), firstName, lastName, address, state, zipCode);
			}

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
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
			PreparedStatement ps = db.conn.prepareStatement(
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
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	public Response updateRoom (Room room) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("UPDATE `room` " +
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
			this.logger.severe(e.getMessage());
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
			PreparedStatement ps = db.conn.prepareStatement(
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
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	public ArrayList<Reservation> getReservationByGuestId(UUID customerId) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		// Build the query
		try {
			PreparedStatement ps = db.conn.prepareStatement(
					"SELECT * FROM `reservation` WHERE `customerId`=?;"
			);
			ps.setString(1, customerId.toString());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				// an empty set could be normal
				return reservations;
			}

			do {

				UUID reservationId = UUID.fromString(rs.getString("id"));
				UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
				int roomId = rs.getInt("roomId");
				LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
				LocalDate arrival = LocalDate.parse(rs.getString("arrival"));
				LocalDate departure = LocalDate.parse(rs.getString("departure"));
				ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

				reservations.add(new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status));
			} while (rs.next());

			return reservations;

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return reservations;
	}



	public Response insertReservation(Reservation r) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("INSERT INTO `reservation` " +
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
			db.logger.severe(e.getMessage());
		}

		// failure
		return Response.FAILURE;
	}

	public Response insertInvoice(Invoice i) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("INSERT INTO `invoice` " +
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
			db.logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	public Invoice getInvoice(UUID invoiceId) {
		// Build the query
		try {
			PreparedStatement ps = db.conn.prepareStatement(
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
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	public Response updateInvoice(Invoice i) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("UPDATE `invoice` " +
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
			db.logger.severe(e.getMessage());
		}
		// failure
		return Response.FAILURE;
	}

	public Response updateReservation(Reservation r) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("UPDATE `reservation` " +
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
			db.logger.severe(e.getMessage());
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
			PreparedStatement ps = db.conn.prepareStatement("DELETE FROM `invoice` " +
					"WHERE `id`=?;");

			ps.setString(1, r.getInvoiceId().toString());

			// Execute the query
			ps.executeQuery();

			// remove the reservation
			ps = db.conn.prepareStatement("DELETE FROM `reservation` " +
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
			db.logger.severe(e.getMessage());
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
			PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM `reservation` WHERE " +
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

			do {
				UUID reservationId = UUID.fromString(rs.getString("id"));
				UUID customerId = UUID.fromString(rs.getString("customerId"));
				UUID invoiceId = UUID.fromString(rs.getString("invoiceId"));
				int roomId = rs.getInt("roomId");
				LocalDate createdAt = LocalDate.parse(rs.getString("createdAt"));
				ReservationStatus status = ReservationStatus.valueOf(rs.getString("status"));

				reservations.add(new Reservation(reservationId, customerId, invoiceId, roomId, createdAt, arrival, departure, status));
			} while (rs.next());

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
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
			PreparedStatement ps = db.conn.prepareStatement("SELECT `password` FROM `user` WHERE `username`=?;");
			ps.setString(1, username.toLowerCase());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for username: " + username);
				return null;
			}

			return rs.getString("password");

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	public Response insertUser(User user, String hashed_password) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("INSERT INTO `user`" +
					" (`id`, `type`, `username`, `password`, `firstName`, `lastName`, `active`) " +
					"values (?,?,?,?,?,?,?)");

			ps.setString(1, user.getUserId().toString());
			ps.setString(2, user.getAccountType().name());
			ps.setString(3, user.getUsername().toLowerCase());
			ps.setString(5, user.getFirstName());
			ps.setString(6, user.getLastName());
			ps.setBoolean(7, user.getActive());

			ps.setString(4, hashed_password);

			// Execute the query
			if (ps.executeUpdate() > 0) {
				return Response.SUCCESS;
			}

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * inserts a user into the database
	 * @param username the username
	 * @param hashed_password the prehashed/salted password
	 * @param fName first name
	 * @param lName lastname
	 * @return
	 */
	public User insertUser(String username, String hashed_password,
							   String fName, String lName, String address, String state, String zipCode) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("INSERT INTO `user`" +
					" (`id`, `type`, `username`, `password`, `firstName`, `lastName`, `street`, `state`, `zip`, " +
					"`active`) " +
					"values (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, String.valueOf(UUID.randomUUID())); // id
			ps.setString(2, "GUEST"); // type
			ps.setString(3, username.toLowerCase()); // username
			ps.setString(4, hashed_password); // password
			ps.setString(5, fName); // firs name
			ps.setString(6, lName); // last name
			ps.setString(7, address); // address
			ps.setString(8, state); // city
			ps.setString(9, zipCode); // zip code
			ps.setBoolean(10, true); // active

			// Execute the query
			if (ps.executeUpdate() == 1) {
				return db.getUser(username);
			};
		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return null;
	}

	/**
	 * updates all attributes of a user profile in the database
	 * @return
	 */
	public Response updateUserProfile(UUID userId, String newUsername, String firstName, String lastName,
									  String address, String state, String zipCode, boolean active){

		try {
			PreparedStatement ps = this.conn.prepareStatement("UPDATE `user` " +
					"SET `username`=?, `firstName`=?, `lastName`=?, `street` = ?, `state`=?, `zip`=?, `active`=? " +
					"WHERE `id` =?");

			ps.setString(1, newUsername);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, address);
			ps.setString(5, state);
			ps.setString(6, zipCode);
			ps.setBoolean(7, active);
			ps.setString(8, String.valueOf(userId));

			// Execute the query
			if (ps.executeUpdate() > 0) {
				return Response.SUCCESS;
			};
		} catch (SQLException e) {
			this.logger.severe(e.getMessage());
		}

		return Response.FAILURE;
	}

	/**
	 * updates the password for a user in the database
	 * @param username the username to match on
	 * @return
	 */
	public Response updatePassword(String username, String newPasswordHash) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
			PreparedStatement ps = null;
			try {
				ps = db.conn.prepareStatement("UPDATE `user` SET `password` = ? WHERE `username`=?;");
				ps.setString(1, newPasswordHash);
				ps.setString(2, username.toLowerCase());
				if (ps.executeUpdate() == 1) {
					return Response.SUCCESS;
				}
			} catch (SQLException e) {
				db.logger.severe(e.getMessage());
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
				db.logger.severe("Unable to locate resource: " + sqlFile);
				return Response.FAILURE;
			}

			String query;
			try {
				query = readFromInputStream(resource);
				resource.close();
			} catch (IOException e) {
				db.logger.severe("Unable to read from resource: " + sqlFile);
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

public ArrayList<Room> getAllRooms() {
		ArrayList<Room> allRooms = new ArrayList<Room>();

		try {
			// Query to pull all room information from db
			PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM `room`");
			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				return allRooms;
			}

			do {
				int roomId = rs.getInt("id");
				boolean smoking = rs.getBoolean("smoking");
				int numBeds = rs.getInt("numBeds");
				Bed bedType = Bed.valueOf(rs.getString("bedType"));
				boolean occupied = rs.getBoolean("occupied");
				double nightly_rate = rs.getDouble("nightlyRate");

				allRooms.add(new Room(roomId, bedType, numBeds, smoking, occupied, nightly_rate));
			} while (rs.next());

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return allRooms;
	}

}