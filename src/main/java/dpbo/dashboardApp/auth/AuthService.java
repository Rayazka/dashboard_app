package dpbo.dashboardApp.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import dpbo.dashboardApp.db.UserDbController;

public class AuthService extends UserDbController {
	
	private Connection connection;
	private int id;
	private boolean isLoggedIn;

	public AuthService() throws Exception {
		super();
		try {
			this.connection = super.getConnection();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize AuthService: " + e.getMessage(), e);
		}
	}

	public boolean login(String username) {
		try {
			// Check if the user exists in the database

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id FROM User WHERE name = '" + username + "'");

			if (resultSet.next()) {
				this.id = resultSet.getInt("id");
				this.isLoggedIn = true;
				return true;
			} else {
				this.isLoggedIn = false;
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Login failed: " + e.getMessage(), e);
		}
	}

	public int getUserId() {
		if (!isLoggedIn) {
			throw new IllegalStateException("User is not logged in.");
		}
		return id;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}


}
