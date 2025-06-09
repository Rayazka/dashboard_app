package dpbo.dashboardApp.db;

import java.sql.Statement;

import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Kelas yang bertanggung jawab untuk mengelola pengguna dalam database.
 * Kelas ini merupakan turunan dari {@see DatabaseManager} untuk berinteraksi dengan database pengguna.
 * 
 */
public class UserDbController extends DatabaseManager {

	private Connection connection;

	/**
	 * Konstruktor untuk inisialisasi UserDbController.
	 * Menginisialisasi koneksi ke database.
	 * 
	 * @throws Exception jika terjadi kesalahan saat menginisialisasi koneksi
	 */
	public UserDbController() throws Exception {
		super();
		this.connection = super.getConnection();
	}

	/**
	 * Metode untuk mendapatkan ID pengguna berdasarkan nama pengguna.
	 * 
	 * @param username Nama pengguna yang ingin dicari ID-nya
	 * @return ID pengguna jika ditemukan
	 * @throws Exception jika terjadi kesalahan saat mencari ID pengguna
	 */
	public int getIdFromUsername(String username) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT id FROM User WHERE name = '" + username + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new ProjectNotFoundException("Project with username " + username + " not found.");
		}
	}

	/**
	 * Metode untuk mendapatkan ID pengguna berdasarkan email.
	 * 
	 * @param email Alamat email pengguna yang ingin dicari ID-nya
	 * @return ID pengguna jika ditemukan
	 * @throws Exception jika terjadi kesalahan saat mencari ID pengguna
	 */
	public int getIdFromEmail(String email) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT id FROM User WHERE email = '" + email + "'");
		if (res.next()) {
			return res.getInt("id");
		} else {
			throw new ProjectNotFoundException("Project with email " + email + " not found.");
		}
	}

	/**
	 * Metode untuk mendapatkan nama pengguna berdasarkan ID pengguna.
	 * 
	 * @param id ID pengguna yang ingin dicari namanya
	 * @return Nama pengguna jika ditemukan
	 * @throws Exception jika terjadi kesalahan saat mencari nama pengguna
	 */
	public String getName(int id) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT name FROM User WHERE id = " + id);
		if (res.next()) {
			return res.getString("name");
		} else {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	/**
	 * Metode untuk mengupdate nama pengguna berdasarkan ID pengguna.
	 * 
	 * @param id ID pengguna yang ingin diupdate namanya
	 * @param name Nama baru yang akan diupdate
	 * @throws Exception jika terjadi kesalahan saat mengupdate nama pengguna
	 */
	public void setName(int id, String name) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE User SET name = '" + name + "' WHERE id = " + id);
		if (rowsAffected == 0) {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	/**
	 * Metode untuk mendapatkan alamat email pengguna berdasarkan ID pengguna.
	 * 
	 * @param id ID pengguna yang ingin dicari email-nya
	 * @return Alamat email pengguna jika ditemukan
	 * @throws Exception jika terjadi kesalahan saat mencari email pengguna
	 */
	public String getEmail(int id) throws Exception {
		Statement statement = connection.createStatement();

		ResultSet res = statement.executeQuery("SELECT email FROM User WHERE id = " + id);
		if (res.next()) {
			return res.getString("email");
		} else {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	/**
	 * Metode untuk mengupdate alamat email pengguna berdasarkan ID pengguna.
	 * 
	 * @param id ID pengguna yang ingin diupdate email-nya
	 * @param email Alamat email baru yang akan diupdate
	 * @throws Exception jika terjadi kesalahan saat mengupdate email pengguna
	 */
	public void setEmail(int id, String email) throws Exception {
		Statement statement = connection.createStatement();
		int rowsAffected = statement.executeUpdate("UPDATE User SET email = '" + email + "' WHERE id = " + id);
		if (rowsAffected == 0) {
			throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		}
	}

	/**
	 * Metode untuk menginisialisasi database.
	 * 
	 * <p>Metode ini tidak didukung oleh kelas {@code ProjectDbController}.
	 * Jika dipanggil, metode ini akan melemparkan {@code UnsupportedOperationException}.
	 * 
	 * @throws UnsupportedOperationException jika metode ini dipanggil.
	 */
	@Override
	public void initializeDatabase() throws Exception {
		throw new UnsupportedOperationException("ProjectDbController does not support this operation.");
	}
}
