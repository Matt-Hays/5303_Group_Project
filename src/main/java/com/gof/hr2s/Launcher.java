package com.gof.hr2s;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.ui.UserLogin;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Launcher extends JFrame {

	private int loggedInUser = -1;
	private Logger logger;
	private Database db = null;
	private final String dbName = "hr2s.sqlite";
	private JPanel userHomePanel;
	private JLabel panelTextHeader;
	private JTextField username;
	private JLabel usernameTxt;
	private JPasswordField passwordField1;
	private JButton loginBtn;


	public static void main(String args[]){
//		Launcher launcher = new Launcher();

		// Calling the entry point (UserLogin) panel
		UserLogin homePanel = new UserLogin();
		homePanel.setVisible(true);
    }

	private boolean initialize() {

		// TODO: Setup a single logging framwork for the whole app
		// setup logging for the framework
		this.logger = Logger.getLogger(Database.class.getName());

		db = new Database(dbName);
		boolean result = db.connect();
		if (result) {
			logger.log(Level.INFO, "DB connect success");
		} else {
			logger.log(Level.SEVERE,"DB connect fail");
			return false;
		}
		return true;
	}
}

