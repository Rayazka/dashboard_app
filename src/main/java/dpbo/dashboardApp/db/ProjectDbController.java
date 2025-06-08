package dpbo.dashboardApp.db;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

public class ProjectDbController extends UserDbController {

	private Connection connection;

	public ProjectDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	public ArrayList<Integer> getProjectIdOwnedByUser(int userId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT id FROM Project WHERE owner_id = " + userId);
			ArrayList<Integer> projectIds = new ArrayList<Integer>();
			while (resultSet.next()) {
				projectIds.add(resultSet.getInt("id"));
			}
			return projectIds;
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve projects owned by user: " + e.getMessage(), e);
		}
	}

	public String getTitle(int proectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT title FROM Project WHERE id = " + proectId);
			if (resultSet.next()) {
				return resultSet.getString("title");
			} else {
				throw new RuntimeException("Project with ID " + proectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project title: " + e.getMessage(), e);
		}
	}

	public String setTitle(int projectId, String title) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET title = '" + title + "' WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
			return title;
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project title: " + e.getMessage(), e);
		}
	}

	public String getDescription(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT description FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				return resultSet.getString("description");
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project description: " + e.getMessage(), e);
		}
	}

	public String setDescription(int projectId, String description) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET description = '" + description + "' WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
			return description;
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project description: " + e.getMessage(), e);
		}
	}

	public LocalDateTime getDeadline(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT deadline FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				return resultSet.getTimestamp("deadline").toLocalDateTime();
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project deadline: " + e.getMessage(), e);
		}
	}

	public void setDeadline(int projectId, LocalDateTime deadline) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET deadline = '" + deadline + "' WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project deadline: " + e.getMessage(), e);
		}
	}

	public String getStatus(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT status FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				return resultSet.getString("status");
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project status: " + e.getMessage(), e);
		}
	}

	public void setStatus(int projectId, String status) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET status = '" + status + "' WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project status: " + e.getMessage(), e);
		}
	}

	public int getOwnerId(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT owner_id FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				int ownerId = resultSet.getInt("owner_id");
				return ownerId;
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project owner: " + e.getMessage(), e);
		}
	}

	public void setOwner(int projectId, int ownerId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET owner_id = " + ownerId + " WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project owner: " + e.getMessage(), e);
		}
	}
}
