package dpbo.dashboardApp.db;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Kelas yang bertanggung jawab untuk mengelola operasi database terkait proyek.
 * Kelas ini merupakan turunan dari {@see DatabaseManager} untuk berinteraksi dengan database proyek.
 * 
 */
public class ProjectDbController extends DatabaseManager {

	// Objek Connection untuk berinteraksi dengan database
	private Connection connection;

	/**
	 * Konstruktor untuk inisialisasi ProjectDbController.
	 * Menginisialisasi koneksi ke database.
	 * 
	 * @throws Exception jika terjadi kesalahan saat menginisialisasi koneksi
	 */
	public ProjectDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	/**
	 * Membuat proyek baru dengan judul, deskripsi, tenggat waktu, ID pemilik, dan tipe proyek.
	 * 
	 * @param title Judul proyek
	 * @param description Deskripsi proyek
	 * @param deadline Tenggat waktu proyek
	 * @param ownerId ID pemilik proyek
	 * @param type Tipe proyek
	 * @return ID proyek yang baru dibuat
	 * @throws Exception jika terjadi kesalahan saat membuat proyek
	 */
	public int createNewProject(String title, String description, LocalDateTime deadline, int ownerId, String type) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("INSERT INTO Project (title, description, deadline, owner_id, type) VALUES ('" + title + "', '" + description + "', '" + deadline + "', " + ownerId + ", '" +type + "')", Statement.RETURN_GENERATED_KEYS);
			if (rowsAffected == 0) {
				throw new RuntimeException("Failed to create new project.");
			}
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new RuntimeException("Failed to retrieve project ID.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to create new project: " + e.getMessage(), e);
		}
	}

	/**
	 * Menghapus proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek yang akan dihapus
	 * @throws Exception jika terjadi kesalahan saat menghapus proyek
	 */
	public void removeProject(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("DELETE FROM Project WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to remove project: " + e.getMessage(), e);
		}
	}

	/**
	 * Mendapatkan nilai dari atribut tertentu pada proyek berdasarkan ID.
	 * 
	 * @param projectId ID proyek
	 * @param field Nama field yang ingin diambil nilainya
	 * @return Nilai dari field yang diminta
	 * @throws Exception jika terjadi kesalahan saat mengambil nilai field
	 */
	public String get(int projectId, String field) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT " + field + " FROM Project WHERE id = " + projectId);
			if (!resultSet.next()) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
			return resultSet.getString(field);
			// Process the result as needed
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project field: " + e.getMessage(), e);
		}
	}

	/**
	 * Mendapatkan daftar ID proyek yang dimiliki oleh pengguna tertentu.
	 * 
	 * @param userId ID pengguna
	 * @return Daftar ID proyek yang dimiliki oleh pengguna
	 * @throws Exception jika terjadi kesalahan saat mengambil ID proyek
	 */
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

	/**
	 * Mendapatkan judul proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return Judul proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil judul proyek
	 */
	public String getTitle(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT title FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				return resultSet.getString("title");
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project title: " + e.getMessage(), e);
		}
	}

	/**
	 * Mengatur judul proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @param title Judul baru untuk proyek
	 * @return Judul yang telah diperbarui
	 * @throws Exception jika terjadi kesalahan saat memperbarui judul proyek
	 */
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

	/**
	 * Mendapatkan tipe proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return Tipe proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil tipe proyek
	 */
	public String getType(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT type FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				return resultSet.getString("type");
			} else {
				throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Mengatur tipe proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @param type Tipe baru untuk proyek
	 * @throws Exception jika terjadi kesalahan saat memperbarui tipe proyek
	 */
	public void setType(int projectId, String type) throws Exception {
		Statement statement = connection.createStatement();
		try {
			int rowsAffected = statement.executeUpdate("UPDATE Project SET type = '" + type + "' WHERE id = " + projectId);
			if (rowsAffected == 0) {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to update project type: " + e.getMessage(), e);
		}
	}

	/**
	 * Mendapatkan deskripsi proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return Deskripsi proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil deskripsi proyek
	 */
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

	/**
	 * Mengatur deskripsi proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @param description Deskripsi baru untuk proyek
	 * @return Deskripsi yang telah diperbarui
	 * @throws Exception jika terjadi kesalahan saat memperbarui deskripsi proyek
	 */
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

	/**
	 * Mendapatkan tenggat waktu proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return Tenggat waktu proyek sebagai LocalDateTime
	 * @throws Exception jika terjadi kesalahan saat mengambil tenggat waktu proyek
	 */
	public LocalDateTime getDeadline(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT deadline FROM Project WHERE id = " + projectId);
			if (resultSet.next()) {
				String str = resultSet.getString("deadline");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				
				return  format.parse(str).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			} else {
				throw new RuntimeException("Project with ID " + projectId + " not found.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve project deadline: " + e.getMessage(), e);
		}
	}

	/**
	 * Mengatur tenggat waktu proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @param deadline Tenggat waktu baru untuk proyek
	 * @throws Exception jika terjadi kesalahan saat memperbarui tenggat waktu proyek
	 */
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

	/**
	 * Mendapatkan status proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return Status proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil status proyek
	 */
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

	/**
	 * Mengatur status proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @param status Status baru untuk proyek
	 * @throws Exception jika terjadi kesalahan saat memperbarui status proyek
	 */
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

	/**
	 * Mendapatkan ID pemilik proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek
	 * @return ID pemilik proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil ID pemilik proyek
	 */
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

	/**
	 * Mengatur pemilik proyek berdasarkan ID proyek dan ID pemilik baru.
	 * 
	 * @param projectId ID proyek
	 * @param ownerId ID pemilik baru untuk proyek
	 * @throws Exception jika terjadi kesalahan saat memperbarui pemilik proyek
	 */
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
