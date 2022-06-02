package com.gof.hr2s;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.ui.UserLogin;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Launcher extends JFrame {
//	private Logger logger;
//	private Database db = null;
//	private final String dbName = "hr2s.sqlite";

	public static void main(String args[]){
//		Launcher launcher = new Launcher();

		// Calling the entry point (UserLogin) panel
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try{
					UserLogin homePanel = new UserLogin();
					homePanel.setVisible(true);
				}catch (Exception ex){
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

