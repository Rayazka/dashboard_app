package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

/**
 * Kelas ini merupakan turunan dari kelas {@see Project} yang
 * merepresentasikan proyek jenis web.
 * 
 */
public class WebProject extends Project {

    // Field untuk menyimpan nama domain yang digunakan dalam proyek web
    private String domainName;

    /**
     * Konstruktor untuk inisialisasi WebProject.
     * 
     * @param id ID proyek
     * @param domainName Nama domain yang digunakan dalam proyek web
     * @throws Exception jika terjadi kesalahan saat menginisialisasi proyek
     */
    public WebProject(int id,String domainName) throws Exception {
        super(id);
        this.domainName = domainName;
    }

    /**
     * Mendapatkan nama domain yang digunakan dalam proyek web.
     * 
     * @return Nama domain proyek.
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Mengatur nama domain yang digunakan dalam proyek web.
     * 
     * @param domain Nama domain yang akan diatur.
     */
    public void setDomainName(String domain) {
        this.domainName = domain;
    }

    /**
     * Mengembalikan representasi string dari proyek web, termasuk informasi
     * dari kelas induk dan nama domain.
     * 
     * @return Representasi string dari proyek web.
     */
    @Override
    public String toString() {
        try {
            return super.toString() + ", Domain: " + domainName;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    /**
     * Menampilkan detail proyek dengan informasi lengkap.
     * Jika terjadi kesalahan dalam pemrosesan, akan ditampilkan pesan error.
     */
    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Web Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    /**
     * Menghitung estimasi anggaran proyek berdasarkan jumlah revisi yang dilakukan.
     * Anggaran dasar adalah 1000.0, dan setiap revisi menambah biaya sebesar 150.
     *
     * @return estimasi anggaran dalam bentuk double. Jika terjadi kesalahan, nilai default adalah 0.0.
     */
    @Override
    public double calculateEstimateBudget() {
        try {
            return 1000.0 + getRevision().size() * 150;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Menghitung estimasi tanggal penyelesaian proyek dengan mengurangi 3 hari dari deadline yang ditentukan.
     *
     * @return tanggal estimasi penyelesaian proyek dalam format LocalDateTime.
     * Jika terjadi kesalahan, akan dikembalikan tanggal dan waktu saat ini sebagai nilai default.
     */
    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(3);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
