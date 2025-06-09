package dpbo.dashboardApp.models;

/**
 * Antarmuka untuk mendefinisikan kontrak untuk kelas-kelas yang
 * merepresentasikan proyek dalam aplikasi dashboard.
 * 
 */
public interface IProject {

    /**
     * Menambahkan revisi baru ke proyek.
     * 
     * @param revision Objek revisi yang akan ditambahkan ke proyek.
     */
    void addRevision(Revision revision);

    /**
     * Menghapus revisi dari proyek berdasarkan objek revisi yang diberikan.
     * 
     * @param revision Objek revisi yang akan dihapus dari proyek.
     */
    void removeRevision(Revision revision);

    /**
     * Mencari revisi dalam proyek berdasarkan ID revisi.
     * 
     * @param revisionId ID revisi yang ingin dicari.
     */
    void findRevision(String revisionId);
}
