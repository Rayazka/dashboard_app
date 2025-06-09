package dpbo.dashboardApp.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import dpbo.dashboardApp.db.UserDbController;

/**
 * Kelas yang bertanggung jawab untuk menangani proses autentikasi pengguna.
 * Kelas ini merupakan turunan dari {@see UserDbController} untuk berinteraksi dengan database pengguna.
 * 
 */

public class AuthService extends UserDbController {
	
	// Objek Connection untuk berinteraksi dengan database
	private Connection connection;

	// Variabel untuk menyimpan ID pengguna
	private int id;

	// Status login pengguna
	private boolean isLoggedIn;

	/**
	 * Konstruktor untuk inisialisasi AuthService.
	 * Menginisialisasi koneksi ke database.
	 * 
	 * @throws Exception jika terjadi kesalahan saat menginisialisasi koneksi
	 */
	public AuthService() throws Exception {
		super();
		try {
			this.connection = super.getConnection();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize AuthService: " + e.getMessage(), e);
		}
	}

	/**
	 * Metode untuk melakukan login pengguna berdasarkan nama pengguna.
	 * 
	 * @param username Nama pengguna yang ingin login
	 * @return true jika login berhasil, false jika tidak
	 */
	public boolean login(String username) {
		try {
			// Cek jika user sudah ada di db
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

	/**
	 * Metode untuk mendapatkan ID pengguna yang sedang login.
	 * 
	 * @return ID pengguna
	 * @throws IllegalStateException jika pengguna belum login
	 */
	public int getUserId() {
		if (!isLoggedIn) {
			throw new IllegalStateException("User is not logged in.");
		}
		return id;
	}

	/**
	 * Metode untuk memeriksa apakah pengguna sudah login.
	 * 
	 * @return true jika pengguna sudah login, false jika belum
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
}
