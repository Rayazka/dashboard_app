package dpbo.dashboardApp.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dpbo.dashboardApp.db.DatabaseManager;
import dpbo.dashboardApp.db.ProjectDbController;
/**
 * Kelas abstrak {@see Project} merepresentasikan proyek umum dalam aplikasi.
 * 
 * Kelas ini menyediakan atribut dan metode dasar yang dapat digunakan oleh
 * kelas turunan seperti {@see DesktopProject}, {@see MobileProject}, dan {@see WebProject}.
 * 
 */
public abstract class Project extends ProjectDbController  implements IProject{

    // Atribut
    private int id; // ID proyek
    private String title; // Judul proyek
    private String description; // Deskripsi proyek

    //Daftar revisi proyek, disimpan dalam bentuk map dengan ID revisi sebagai kunci
    private Map<Integer, Revision> revisions;
    private int ownerId; // ID pemilik proyek
    private LocalDateTime deadline; // Batas waktu proyek
    
    // Koneksi ke database
    private Connection connection;

    /**
     * Konstruktor untuk menginisialisasi atribut proyek dengan mengambil data
     * dari database berdasarkan ID proyek.
     * 
     * @param id ID proyek.
     * @throws Exception jika terjadi kesalahan saat mengambil data dari database.
     */
    public Project(int id) throws Exception {
        super();
        try {
            this.id = id;
			
			this.title = super.getTitle(id);
			this.description = super.getDescription(id);
			this.ownerId = super.getOwnerId(id);
			this.deadline = super.getDeadline(id);

            // Inisialisasi daftar revisi proyek
            this.revisions = new HashMap<Integer,Revision>();
            this.connection = super.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menambahkan revisi baru ke dalam daftar revisi proyek jika belum ada.
     * Jika revisi sudah ada atau bernilai null, akan menampilkan pesan kesalahan.
     *
     * @param revision Objek revisi yang akan ditambahkan.
     */
    @Override
    public void addRevision(Revision revision) {
        // Pastikan revisi tidak null dan belum ada dalam daftar
        if (revision != null && !revisions.containsKey(revision.getId())) {
            revisions.put(revision.getId(), revision);
        } else {
            System.out.println("Revisi gagal ditambahkan (sudah ada atau null).");
        }
    }

    /**
     * Menghapus revisi dari daftar jika revisi tersebut ada.
     * Jika tidak ditemukan, akan menampilkan pesan kesalahan.
     *
     * @param revision Objek revisi yang akan dihapus.
     */
    @Override
    public void removeRevision(Revision revision) {
        // Pastikan revisi tidak null dan memang ada dalam daftar
        if (revision != null && revisions.containsKey(revision.getId())) {
            revisions.remove(revision.getId());
        } else {
            System.out.println("Revisi tidak ditemukan untuk dihapus.");
        }
    }
    
    /**
     * Mencari revisi berdasarkan ID yang diberikan.
     * Jika ID tidak valid atau revisi tidak ditemukan, akan menampilkan pesan kesalahan.
     *
     * @param revisionId ID revisi dalam bentuk string.
     */
    @Override
    public void findRevision(String revisionId) {
        // Validasi apakah ID revisi kosong atau null
        if (revisionId == null || revisionId.trim().isEmpty()) {
            System.out.println("ID revisi tidak valid.");
            return;
        }
        
        // Konversi ID revisi ke integer dan lakukan pencarian
        Revision revision = revisions.get(Integer.parseInt(revisionId));
        if (revision != null) {
            System.out.println("Revisi ditemukan: " + revision);
        } else {
            System.out.println("Revisi dengan ID " + revisionId + " tidak ditemukan.");
        }
    }

    /**
     * Mendapatkan ID proyek.
     * 
     * @return ID proyek.
     */
    public int getId() {
        return id;
    }

    /**
     * Mendapatkan judul proyek.
     * 
     * @return Judul proyek.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mengatur judul proyek.
     * 
     * @param title Judul baru untuk proyek.
     * @throws Exception jika terjadi kesalahan saat memperbarui judul di database.
     */
    public void setTitle(String title) throws Exception {
        this.title = title;
		super.setTitle(id, title);
    }

    /**
     * Mendapatkan deskripsi proyek.
     * 
     * @return Deskripsi proyek.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mengatur deskripsi proyek.
     * 
     * @param desc Deskripsi baru untuk proyek.
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * Mendapatkan ID pemilik proyek.
     * 
     * @return ID pemilik proyek.
     */
    public int getOwner() {
        return ownerId;
    }
    
    /**
     * Mengatur ID pemilik proyek.
     * 
     * @param owner ID pemilik baru untuk proyek.
     * @throws Exception jika terjadi kesalahan saat memperbarui pemilik di database.
     */
    public void setOwner(int owner) throws Exception {
        this.ownerId = owner;
		super.setOwner(id, owner);
    }

    /**
     * Mendapatkan tenggat waktu proyek.
     * 
     * @return Tenggat waktu proyek.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Mengatur tenggat waktu proyek.
     * 
     * @param deadline Tenggat waktu baru untuk proyek.
     * @throws Exception jika terjadi kesalahan saat memperbarui tenggat waktu di database.
     */
    public void setDeadline(LocalDateTime deadline) throws Exception {
        this.deadline = deadline;
		super.setDeadline(id, deadline);
    }

    /**
     * Mendapatkan daftar revisi proyek.
     * 
     * @return Daftar revisi proyek dalam bentuk {@code List<Revision>}.
     */
    public List<Revision> getRevision() {
        return new ArrayList<Revision>(revisions.values());
    }

    /**
     * Mengembalikan representasi string dari proyek.
     * 
     * @return Representasi string dari proyek, termasuk ID, judul, dan ID pemilik.
     */    
    @Override
    public String toString() {
        return "Project ID: " + id + ", Title: " + title + ", Client: " + ownerId;
    }
    
    /**
     * Menampilkan detail proyek.
     * 
z     */
    public abstract void displayProjectDetails();

    /**
     * Menghitung estimasi anggaran proyek.
     * 
     * @return Estimasi anggaran proyek.
     */
    public abstract double calculateEstimateBudget();

    /**
     * Menghitung estimasi tanggal penyelesaian proyek.
     * 
     * @return Estimasi tanggal penyelesaian proyek.
     */
    public abstract LocalDateTime calculateEstimateProjectComplete();
}
