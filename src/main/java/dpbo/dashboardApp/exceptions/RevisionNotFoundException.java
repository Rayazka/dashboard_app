package dpbo.dashboardApp.exceptions;

/**
 * Kelas yang bertanggung jawab untuk menangani pengecualian ketika proyek tidak ditemukan.
 * Kelas ini merupakan turunan dari {@see Exception} untuk memberikan informasi spesifik tentang kesalahan proyek.
 * 
 */
public class RevisionNotFoundException extends Exception{
	public RevisionNotFoundException(String e) {
		super(e);
	}
}
