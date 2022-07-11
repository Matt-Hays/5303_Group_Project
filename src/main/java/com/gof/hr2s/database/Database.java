package com.gof.hr2s.database;

import com.gof.hr2s.models.*;
import com.gof.hr2s.service.Response;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Database {

	private final String dbName = "hr2s.sqlite";
	private Connection conn = null;
	private Logger logger;

	private static Database db = null;

	public static Database Database() {
		if (null == db) {
			db = new Database();
			db.logger = Logger.getLogger(Database.class.getName());
			db.connect();
		}

		return db;
	}

	public Response connect() {
		File dbFile = new File(dbName);
		boolean exists = dbFile.exists();

		try {
			String url = "jdbc:sqlite:" + dbName;
			// create a connection to the database
			db.conn = DriverManager.getConnection(url);

			if (!exists) {
				db.logger.info("DB " + dbName + " does not exist.");
				return dbInit();
			} else {
				db.logger.info("DB " + dbName + " exists.");
			}

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
			return Response.FAILURE;
		}

		return Response.SUCCESS;
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
					"SELECT `id`, `type`, `firstName`, `lastName`, `active` FROM `user` WHERE `username`=?;"
			);
			ps.setString(1, username.toLowerCase());

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for username: " + username);
				return null;
			}

			int userId = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			boolean active = rs.getBoolean("active");
			Account accountType = Account.valueOf(rs.getString("type"));

			return new User(userId, accountType, username.toLowerCase(), firstName, lastName);

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
					"SELECT `bedType`, `numBeds`, `smoking`, `occupied`, `nightlyRate` FROM `room` WHERE `id`=?;"
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

	/**
	 * recall a reservation
	 * @param reservationId the reservation to look up
	 * @return a Reservation instance or null
	 */
	public Reservation getRegistration(int reservationId) {
		// Build the query
		try {
			PreparedStatement ps = db.conn.prepareStatement(
					"SELECT * FROM `registration` WHERE `id`=?;"
			);
			ps.setInt(1, reservationId);

			// Execute the query
			ResultSet rs = ps.executeQuery();
			if (!validate(rs)) {
				logger.info("Empty set for registration: " + reservationId);
				return null;
			}

			int userId = rs.getInt("userId");
			int roomId = rs.getInt("roomId");
			LocalDate bookTime = LocalDate.parse(rs.getString("bookTime"));
			LocalDate startTime = LocalDate.parse(rs.getString("startTime"));
			LocalDate endTime = LocalDate.parse(rs.getString("endTime"));
			String checkIn = rs.getString("checkIn");
			String checkOut = rs.getString("checkOut");

			// user has not checked in for reservation
			if (null == checkIn) {
				return new Reservation(reservationId, userId, roomId, bookTime, startTime, endTime);
			}

			LocalDate ciLocalDate = null;
			LocalDate coLocalDate = null;
			if (null != checkIn) {
				ciLocalDate = LocalDate.parse(checkIn);
				if (null != checkOut) {
					coLocalDate = LocalDate.parse(checkOut);
				}
			}

			// user has checked in for reservation
			return new Reservation(reservationId, userId, roomId, bookTime, startTime, endTime, ciLocalDate, coLocalDate);

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
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

	/**
	 * inserts a user into the database
	 * @param type the type of user
	 * @param username the username
	 * @param hashed_password the prehashed/salted password
	 * @param fName first name
	 * @param lName lastname
	 * @param active is the account active
	 * @return
	 */
	public Response insertUser(Account type, String username, String hashed_password,
							   String fName, String lName, boolean active) {
		try {
			PreparedStatement ps = db.conn.prepareStatement("INSERT INTO `user`" +
					" (`type`, `username`, `password`, `firstName`, `lastName`, `active`) " +
					"values (?,?,?,?,?,?)");
			ps.setString(1, type.name());
			ps.setString(2, username.toLowerCase());
			ps.setString(3, hashed_password);
			ps.setString(4, fName);
			ps.setString(5, lName);
			ps.setBoolean(6, active);

			// Execute the query
			if (ps.executeUpdate() > 0) {
				return Response.SUCCESS;
			};
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
		String []sqlFiles = { "sql/user_tbl.sql", "sql/insert_users.sql", "sql/room_tbl.sql", "sql/insert_rooms.sql" , "sql/registration_tbl.sql"};

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
				double nightly_rate = rs.getDouble("nightly_rate");

				allRooms.add(new Room(roomId, bedType, numBeds, smoking, occupied, nightly_rate));
			} while (rs.next());

		} catch (SQLException e) {
			db.logger.severe(e.getMessage());
		}

		return allRooms;
	}

}