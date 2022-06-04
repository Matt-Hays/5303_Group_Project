package com.gof.hr2s;


import com.gof.hr2s.ui.PrimaryFrame;

import javax.swing.*;

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

