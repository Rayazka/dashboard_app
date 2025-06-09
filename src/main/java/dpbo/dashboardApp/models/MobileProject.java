package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

/**
 * Kelas ini merupakan turunan dari kelas {@see Project} yang
 * merepresentasikan proyek jenis mobile.
 * 
 */
public class MobileProject extends Project {

    // Field untuk menyimpan platform yang digunakan dalam proyek mobile
    private String platform;

    /**
     * Konstruktor untuk inisialisasi MobileProject.
     * 
     * @param id ID proyek
     * @param platform Platform yang digunakan dalam proyek mobile
     * @throws Exception jika terjadi kesalahan saat menginisialisasi proyek
     */
    public MobileProject(int id, String platform) throws Exception {
        super(id);
        this.platform = platform;
    }

    /**
     * Mendapatkan platform yang digunakan dalam proyek mobile.
     * 
     * @return Platform proyek.
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Mengatur platform yang digunakan dalam proyek mobile.
     * 
     * @param platform Platform yang akan diatur.
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Mengembalikan representasi string dari proyek mobile, termasuk informasi
     * dari kelas induk dan platform.
     * 
     * @return Representasi string dari proyek mobile.
     */
    @Override
    public String toString() {
        try {
            return super.toString() + ", Platform: " + platform;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    /**
     * Menampilkan detail proyek mobile ke output standar.
     */
    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Mobile Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    /**
     * Menghitung estimasi anggaran untuk proyek mobile.
     * 
     * <p>Anggaran dasar adalah 1500.0, ditambah 200 untuk setiap revisi proyek.
     * 
     * @return Estimasi anggaran proyek.
     */
    @Override
    public double calculateEstimateBudget() {
        try {
            return 1500.0 + getRevision().size() * 200;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Menghitung estimasi tanggal penyelesaian proyek mobile.
     * 
     * <p>Estimasi penyelesaian adalah dua hari sebelum tenggat waktu proyek.
     * 
     * @return Estimasi tanggal penyelesaian proyek.
     */
    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(2);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
