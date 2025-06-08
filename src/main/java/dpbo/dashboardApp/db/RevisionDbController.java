package dpbo.dashboardApp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dpbo.dashboardApp.exceptions.RevisionNotFoundException;

public class RevisionDbController extends DatabaseManager {
	private Connection connection;

	public RevisionDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	public void createRevision(int projectId, String revisionName, String notes) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("INSERT INTO Revision (project_id, name, notes) VALUES (" + projectId + ", '" + revisionName + "', '" + notes + "')");
		if (rowsAffected == 0) {
			throw new Exception("Failed to create revision for project ID " + projectId);
		}
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

	public String getNotes(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT notes FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getString("notes");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	public String setNotes(int revisionId, String notes) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE Revision SET notes = '" + notes + "' WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
		return notes;
	}

	public LocalDateTime getCreatedAt(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT created_at FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getTimestamp("created_at").toLocalDateTime();
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	public int getProjectId(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT project_id FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getInt("project_id");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}
}
