package dpbo.dashboardApp.db;

import java.sql.Statement;

import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserDbController extends DatabaseManager {

	private Connection connection;

	public UserDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	public int getIdFromUsername(String username) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT id FROM User WHERE name = '" + username + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new ProjectNotFoundException("Project with username " + username + " not found.");
		}
	}

	public int getIdFromEmail(String email) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT id FROM User WHERE email = '" + email + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new ProjectNotFoundException("Project with email " + email + " not found.");
		}
	}

	public String getName(int id) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT name FROM User WHERE id = " + id);
		if (res.next()) {
			return res.getString("name");
		} else {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	public void setName(int id, String name) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE User SET name = '" + name + "' WHERE id = " + id);
		if (rowsAffected == 0) {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	public String getEmail(int id) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT email FROM User WHERE id = " + id);
		if (res.next()) {
			return res.getString("email");
		} else {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	public void setEmail(int id, String email) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE User SET email = '" + email + "' WHERE id = " + id);
		if (rowsAffected == 0) {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	@Override
	public void initializeDatabase() throws Exception {
		throw new UnsupportedOperationException("ProjectDbController does not support this operation.");
	}
}
