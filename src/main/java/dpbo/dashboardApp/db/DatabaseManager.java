package dpbo.dashboardApp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	private final String url = "jdbc:sqlite:sample.db";
	private boolean isConnected = false;
	private Connection connection;

	public DatabaseManager() {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(url);
			isConnected = true;
			
		} catch (SQLException e) {
			System.err.println("Database connection failed: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC Driver not found: " + e.getMessage());
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public Connection getConnection() throws Exception {
		if (!isConnected) {
			throw new Exception("Database connection is not established.");
		}

		return connection;
	}

	public void initializeDatabase() throws Exception {
		if (!isConnected) {
			throw new Exception("Database connection is not established.");
		}

		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); // set timeout to 30 sec.
												//
		// create user table
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS User ("
				+ "id INTEGER PRIMARY KEY,"
				+ "name VARCHAR(100) NOT NULL,"
				+ "email VARCHAR(100) NOT NULL"
				+ ");");

		// create Project table
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Project ("
				+ "id INTEGER PRIMARY KEY,"
				+ "title VARCHAR(100) NOT NULL,"
				+ "description TEXT,"
				+ "deadline DATETIME DEFAULT CURRENT_TIMESTAMP,"
				+ "type TEXT CHECK (type IN ('web', 'mobile', 'desktop')) NOT NULL,"
				+ "FOREIGN KEY (owner_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,"
				+ "STATUS TEXT DEFAULT 'active'"
				);

		// create Revision table
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Revision ("
				+ "id INTEGER PRIMARY KEY,"
				+ "notes TEXT,"
				+ "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
				+ "FOREIGN KEY (project_id) REFERENCES Project(id)"
				);
	}
}
