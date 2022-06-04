package com.gof.hr2s;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Launcher {
//	private Logger logger;
//	private Database db = null;
//	private final String dbName = "hr2s.sqlite";

    public static void main(String args[]) {
        // Start the app
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PrimaryFrame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

//	private boolean initialize() {
//
//		// TODO: Setup a single logging framwork for the whole app
//		// setup logging for the framework
//		this.logger = Logger.getLogger(Database.class.getName());
//
//		db = new Database(dbName);
//		boolean result = db.connect();
//		if (result) {
//			logger.log(Level.INFO, "DB connect success");
//		} else {
//			logger.log(Level.SEVERE,"DB connect fail");
//			return false;
//		}
//		return true;
//	}
}

