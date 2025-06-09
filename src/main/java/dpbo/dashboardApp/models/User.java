package dpbo.dashboardApp.models;

import dpbo.dashboardApp.db.UserDbController;

/**
 * Kelas yang merepresentasikan pengguna dalam aplikasi.
 * Kelas ini merupakan turunan dari {@see UserDbController} untuk berinteraksi dengan database pengguna.
 * 
 */
public class User extends UserDbController {
	// Variabel untuk menyimpan ID pengguna dan nama pengguna
	private int id;
	// Variabel untuk menyimpan nama pengguna
	private String username;

	/**
	 * Konstruktor untuk inisialisasi objek User.
	 * Mengambil ID pengguna dari database berdasarkan nama pengguna.
	 * 
	 * @param username Nama pengguna yang ingin diinisialisasi
	 * @throws Exception jika terjadi kesalahan saat mengambil ID pengguna
	 */
	public User(int id) throws Exception{
		super();
		this.id = id;
		this.username = super.getName(id);
	}

	/**
	 * Mendapatkan ID pengguna
	 * 
	 * @return ID pengguna
	 */
	public int getId() {
		return id;
	}

	/**
	 * Mendapatkan nama pengguna
	 * 
	 * @return Nama pengguna
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Mendapatkan representasi string dari objek User.
	 * 
	 * @return String yang merepresentasikan objek User
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
}
