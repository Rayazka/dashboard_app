package dpbo.dashboardApp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dpbo.dashboardApp.exceptions.RevisionNotFoundException;

public class RevisionDbController extends DatabaseManager {
	private Connection connection;

	public RevisionDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	public int getRevisionId(int projectId, String revisionName) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT id FROM Revision WHERE project_id = " + projectId + " AND name = '" + revisionName + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new RevisionNotFoundException("Revision with name " + revisionName + " not found in project ID " + projectId);
		}
	}

	public ArrayList<Integer> getRevisionIdsFromProject(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT id FROM Revision WHERE project_id = " + projectId);
		ArrayList<Integer> revisionIds = new ArrayList<Integer>();
		while (res.next()) {
			revisionIds.add(res.getInt("id"));
		}
		return revisionIds;
	}

	public String getTitle(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT title FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getString("title");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	public String setTitle(int revisionId, String title) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE Revision SET title = '" + title + "' WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
		return title;
	}

	public String getDescription(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT description FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getString("description");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	public String setDescription(int revisionId, String description) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE Revision SET description = '" + description + "' WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
		return description;
	}

	public String getStatus(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT status FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getString("status");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	public String setStatus(int revisionId, String status) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE Revision SET status = '" + status + "' WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
		return status;
	}
}
