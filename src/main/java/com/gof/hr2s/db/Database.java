package com.gof.hr2s.db;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

	public final String dbName;
	private Connection conn = null;
	private Logger logger;

	public Database(String dbName) {
		this.dbName = dbName;
		this.logger = Logger.getLogger(Database.class.getName());
	}

	public boolean connect() {
		File dbFile = new File(this.dbName);
		boolean exists = dbFile.exists();

		try {
			String url = "jdbc:sqlite:" + dbName;
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			
			if (!exists) {
				logger.info("DB " + dbName + " does not exist.");
				return dbInit();
			} else {
				logger.info("DB " + dbName + " exists.");
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}

		return true;
	}

	public boolean close() {
		try {
			if (conn != null) {
				logger.info("Closing the database.");
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}

		return true;
	}

	private boolean dbInit() {

		logger.info("Initializing the Database.");
		Boolean result = createUserTable();

		return result;
	}

	/**
	 * attempt to execute a sql statement in the db
	 * @param sqlStatement the statement to be executed
	 * @return the results of the execution or null for failure
	 */
	private ResultSet execute(String sqlStatement) {
		ResultSet rs = null;

		try {
			Statement statement = this.conn.createStatement();
			rs = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}

		return rs;
	}

	/**
	 * Reads in the SQL statement to create the user table and passes it to executeQuery
	 * @return true (success) / false (fail)
	 */
	private boolean createUserTable() {
		// read in resource file user_tbl.sql
		String userTbl = "sql/user_tbl.sql";
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream resource = classLoader.getResourceAsStream(userTbl);
		if (resource == null) {
            logger.severe("Unable to locate resource: " + userTbl);
			return false;
        }

		String query;
		try {
			query = readFromInputStream(resource);
			resource.close();
		} catch (IOException e) {
			logger.severe("Unable to read from resource: " + userTbl);
			return false;
		}

		// execute and see if we got back a null object (indicates failure)
		ResultSet rs = execute(query);
		return rs != null;
	}

	/**
	 * reads line by line through an InputStream building a single string along the way
	 * borrowed from <a href="https://www.baeldung.com/reading-file-in-java">baeldung.com</a>
	 * @param inputStream the stream to read from
	 * @return the completed string
	 * @throws IOException Unable to read from InputStream
	 */
	private String readFromInputStream(InputStream inputStream)
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
