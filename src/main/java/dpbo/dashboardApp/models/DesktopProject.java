package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

/**
 * Kelas ini merupakan  turunan dari kelas {@see Project} yang
 * merepresentasikan proyek jenis desktop.
 * 
 */
public class DesktopProject extends Project {

    // Field untuk menyimpan sistem operasi yang digunakan dalam proyek desktop
    private String operationSystem;

    /**
     * Konstruktor untuk inisialisasi DesktopProject.
     * 
     * @param id ID proyek
     * @param operationSystem Sistem operasi yang digunakan dalam proyek desktop
     * @throws Exception jika terjadi kesalahan saat menginisialisasi proyek
     */
    public DesktopProject(int id, String operationSystem) throws Exception {
        super(id);
        this.operationSystem = operationSystem;
    }

    /**
     * Mendapatkan sistem operasi yang digunakan dalam proyek desktop.
     * 
     * @return Sistem operasi proyek.
     */
    public String getOS() {
        return operationSystem;
    }

    /**
     * Mengatur sistem operasi yang digunakan dalam proyek desktop.
     * 
     * @param os Sistem operasi yang akan diatur.
     */
    public void setOS(String os) {
        this.operationSystem = os;
    }

    /**
     * Mengembalikan representasi string dari proyek desktop, termasuk informasi
     * dari kelas induk dan sistem operasi.
     * 
     * @return Representasi string dari proyek desktop.
     */
    @Override
    public String toString() {
        try {
            return super.toString() + ", OS: " + operationSystem;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    /**
     * Menampilkan detail proyek desktop ke output standar.
     * 
     * <p>Detail yang ditampilkan mencakup informasi dari kelas induk dan sistem operasi.
     */
    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Desktop Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    /**
     * Menghitung estimasi anggaran untuk proyek desktop.
     *  
     * @return Estimasi anggaran proyek.
     */
    @Override
    public double calculateEstimateBudget() {
        try {
            return 1200.0 + getRevision().size() * 100;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Menghitung estimasi tanggal penyelesaian proyek desktop.
     * 
     * @return Tanggal dan waktu estimasi penyelesaian proyek.
     */
    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(1);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
