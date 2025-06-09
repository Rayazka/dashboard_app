package dpbo.dashboardApp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dpbo.dashboardApp.exceptions.RevisionNotFoundException;

/**
 * Kelas yang bertanggung jawab untuk mengelola revisi dalam database.
 * Kelas ini merupakan turunan dari {@see DatabaseManager} untuk berinteraksi dengan database revisi.
 * 
 */
public class RevisionDbController extends DatabaseManager {
	private Connection connection;

	/**
	 * Konstruktor untuk inisialisasi RevisionDbController.
	 * Menginisialisasi koneksi ke database. 
	 *  
	 * @throws Exception jika terjadi kesalahan saat menginisialisasi koneksi
	 */ 
	public RevisionDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	/**
	 * Metode untuk membuat revisi baru dalam database.
	 * 
	 * @param projectId ID proyek yang terkait dengan revisi
	 * @param revisionName Nama revisi yang akan dibuat
	 * @param notes Catatan atau deskripsi dari revisi
	 * @throws Exception jika terjadi kesalahan saat membuat revisi
	 */
	public void createRevision(int projectId, String revisionName, String notes) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("INSERT INTO Revision (project_id, notes) VALUES (" + projectId + ", '" + notes + "')");
		if (rowsAffected == 0) {
			throw new Exception("Failed to create revision for project ID " + projectId);
		}
	}

	/**
	 * Metode untuk menghapus revisi berdasarkan ID revisi.
	 * 
	 * @param revisionId ID revisi yang akan dihapus
	 * @throws Exception jika terjadi kesalahan saat menghapus revisi
	 */
	public void removeRevision(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("DELETE FROM Revision WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	/**
	 * Metode untuk mendapatkan ID revisi berdasarkan nama revisi dan ID proyek.
	 * 
	 * @param projectId ID proyek yang terkait dengan revisi
	 * @param revisionName Nama revisi yang ingin dicari
	 * @return ID revisi jika ditemukan
	 * @throws Exception jika terjadi kesalahan atau revisi tidak ditemukan
	 */
	public int getRevisionId(int projectId, String revisionName) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT id FROM Revision WHERE project_id = " + projectId + " AND name = '" + revisionName + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new RevisionNotFoundException("Revision with name " + revisionName + " not found in project ID " + projectId);
		}
	}

	/**
	 * Metode untuk mendapatkan daftar ID revisi dari proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek yang terkait dengan revisi
	 * @return Daftar ID revisi yang terkait dengan proyek
	 * @throws Exception jika terjadi kesalahan saat mengambil data revisi
	 */
	public ArrayList<Integer> getRevisionIdsFromProject(int projectId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT id FROM Revision WHERE project_id = " + projectId);
		ArrayList<Integer> revisionIds = new ArrayList<Integer>();
		while (res.next()) {
			revisionIds.add(res.getInt("id"));
		}
		return revisionIds;
	}

	/**
	 * Metode untuk mendapatkan catatan dari revisi berdasarkan ID revisi.
	 * 
	 * @param revisionId ID revisi yang ingin diambil catatannya
	 * @return Catatan dari revisi
	 * @throws Exception jika terjadi kesalahan atau revisi tidak ditemukan
	 */
	public String getNotes(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT notes FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getString("notes");
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

	/**
	 * Metode untuk mengupdate catatan dari revisi berdasarkan ID revisi.
	 * 
	 * @param revisionId ID revisi yang ingin diupdate catatannya
	 * @param notes Catatan baru yang akan disimpan
	 * @return Catatan yang telah diupdate
	 * @throws Exception jika terjadi kesalahan atau revisi tidak ditemukan
	 */
	public String setNotes(int revisionId, String notes) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE Revision SET notes = '" + notes + "' WHERE id = " + revisionId);
		if (rowsAffected == 0) {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
		return notes;
	}

	/**
	 * Mendapatkan waktu pembuatan revisi berdasarkan ID revisi.
	 * 
	 * @param revisionId ID revisi.
	 * @return Waktu pembuatan revisi dalam bentuk {@code LocalDateTime}.
	 * @throws RevisionNotFoundException jika revisi dengan ID tersebut tidak ditemukan.
	 * @throws Exception jika terjadi kesalahan saat mengambil waktu pembuatan revisi.
	 */
	public LocalDateTime getCreatedAt(int revisionId) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT created_at FROM Revision WHERE id = " + revisionId);
		if (res.next()) {
			return res.getTimestamp("created_at").toLocalDateTime();
		} else {
			throw new RevisionNotFoundException("Revision with ID " + revisionId + " not found.");
		}
	}

/**
	 * Mendapatkan ID proyek yang terkait dengan revisi berdasarkan ID revisi.
	 * 
	 * @param revisionId ID revisi yang ingin diambil ID proyeknya
	 * @return ID proyek yang terkait dengan revisi
	 * @throws RevisionNotFoundException jika revisi dengan ID tersebut tidak ditemukan
	 * @throws Exception jika terjadi kesalahan saat mengambil ID proyek
	 */
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
