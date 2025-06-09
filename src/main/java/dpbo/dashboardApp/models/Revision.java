package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

import dpbo.dashboardApp.db.RevisionDbController;

/**
 * Kelas yang merepresentasikan revisi dalam aplikasi.
 * Kelas ini merupakan turunan dari {@see RevisionDbController} untuk berinteraksi dengan database revisi.
 */
public class Revision extends RevisionDbController{
	// Variabel untuk menyimpan ID revisi, catatan, waktu pembuatan, dan ID proyek terkait
	private int id;
	// Variabel untuk menyimpan catatan revisi
	private String notes;
	// Variabel untuk menyimpan waktu pembuatan revisi
	private LocalDateTime createdAt;
	// Variabel untuk menyimpan ID proyek terkait revisi
	private int ProjectId;

	/**
	 * Konstruktor untuk inisialisasi objek Revision.
	 * Mengambil ID revisi dari database berdasarkan ID yang diberikan.
	 * 
	 * @param id ID revisi yang ingin diinisialisasi
	 * @throws Exception jika terjadi kesalahan saat mengambil data dari database
	 */
	public Revision(int id) throws Exception {
		super();
		this.id = id;
		this.notes = super.getNotes(id);
		this.createdAt = super.getCreatedAt(id);
		this.ProjectId = super.getProjectId(id);
	}

	/**
	 * Mendapatkan ID revisi.
	 * 
	 * @return ID revisi
	 */
	public int getId() {
		return id;
	}

	/**
	 * Mendapatkan catatan revisi.
	 * 
	 * @return Catatan revisi
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Mendapatkan waktu pembuatan revisi.
	 * 
	 * @return Waktu pembuatan revisi
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Mendapatkan ID proyek terkait revisi.
	 * 
	 * @return ID proyek terkait revisi
	 */
	public int getProjectId() {
		return ProjectId;
	}

	/**
	 * Mengatur catatan revisi.
	 * 
	 * @param notes Catatan baru yang akan disimpan
	 * @throws Exception jika terjadi kesalahan saat memperbarui catatan di database
	 */
	public void setNotes(String notes) throws Exception {
		this.notes = super.setNotes(id, notes);
	}

	/**
	 * Representasi string dari objek Revision.
	 * 
	 * @return String yang merepresentasikan objek Revision
	 */
	@Override
	public String toString() {
		return "Revision [id=" + id + "notes="+ notes + ", createdAt=" + createdAt + "]";
	}
}
