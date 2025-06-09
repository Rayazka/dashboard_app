package dpbo.dashboardApp.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dpbo.dashboardApp.db.ProjectDbController;
import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

/**
 * Kelas ProjectManager bertanggung jawab untuk mengelola proyek dalam sistem.
 * Termasuk pembuatan, penghapusan, pencarian, dan pemfilteran proyek berdasarkan klien.
 */
public class ProjectManager {
	// ProjectDbController untuk berinteraksi dengan database proyek
	private ProjectDbController projectDbController;

	/**
	 * Konstruktor untuk inisialisasi ProjectManager.
	 * Menginisialisasi ProjectDbController untuk berinteraksi dengan database proyek.
	 * 
	 * @throws Exception jika terjadi kesalahan saat menginisialisasi ProjectDbController
	 */
        public ProjectManager() throws Exception {
			projectDbController = new ProjectDbController();
        }
        
	/**
	 * Method untuk membuat proyek baru.
	 * 
	 * @param title Judul proyek
	 * @param description Deskripsi proyek
	 * @param deadline Batas waktu proyek
	 * @param client ID klien yang memiliki proyek
	 * @throws Exception jika terjadi kesalahan saat membuat proyek
	 */
        public void newProject(String title, String description, LocalDateTime deadline, int client, String type) throws Exception {
        	projectDbController.createNewProject(title, description, deadline, client, type);
        }

	/**
	 * Method untuk menghapus proyek berdasarkan ID proyek.
	 * 
	 * @param projectId ID proyek yang akan dihapus
	 * @throws ProjectNotFoundException jika proyek dengan ID tersebut tidak ditemukan
	 * @throws Exception jika terjadi kesalahan saat menghapus proyek
	 */
	public void removeProject(int projectId) throws ProjectNotFoundException, Exception {
		// Menghapus project berdasarkan ID
		projectDbController.removeProject(projectId);
		System.out.println("Project dengan ID " + projectId + " telah dihapus.\n");
	}
        
        /**
	 * Menemukan proyek berdasarkan ID yang diberikan.
	 * @param projectId ID proyek yang dicari.
	 * @return Objek Project yang sesuai dengan ID, jika ditemukan.
	 * @throws ProjectNotFoundException jika proyek dengan ID tersebut tidak ditemukan.
	 * @throws Exception jika terjadi kesalahan lainnya.
	 */
        public Project findProjectById(int projectId) throws ProjectNotFoundException, Exception{

		String type = projectDbController.getType(projectId);
		// Jika tidak ditemukan, lempar exception.
		if (type == null) {
			throw new ProjectNotFoundException("Project dengan ID " + projectId + " tidak ditemukan.");
		}

		// Menentukan jenis proyek berdasarkan tipe yang diberikan.
		if (type.equals("web")) {
			return new WebProject(projectId, "http://example.com");
		} else if (type.equals("mobile")) {
			return new MobileProject(projectId, "android");
		} else if (type.equals("desktop")) {
			return new DesktopProject(projectId, "windows");
		} else {
			throw new ProjectNotFoundException("Tipe project tidak dikenali untuk ID " + projectId);
		}
        }
	
	/**
	 * Memfilter proyek berdasarkan ID klien yang memilikinya.
	 * @param clientId ID klien.
	 * @return Daftar proyek yang dimiliki oleh klien tersebut.
	 * @throws Exception jika terjadi kesalahan dalam mengambil data proyek.
	 */
        public List<Project> findProjectsByClient(int clientId) throws Exception {
			
		// Mengambil daftar ID proyek yang dimiliki oleh klien.
		ArrayList<Integer> projects = projectDbController.getProjectIdOwnedByUser(clientId);
		// Inisialisasi daftar proyek yang akan dikembalikan.
		List<Project> projectList = new ArrayList<Project>();
        		
		// Iterasi setiap ID proyek dan coba mendapatkannya.
		for (int projectId : projects) {
			try {
				Project project = findProjectById(projectId);
				projectList.add(project);
			} catch (ProjectNotFoundException e) {
				// Project tidak ditemukan, lanjutkan ke project berikutnya
				new ProjectNotFoundException("Project dengan ID " + projectId + " tidak ditemukan.");
			}
		}
		return projectList;
	}

	/**
	 * Menampilkan semua detail proyek yang dimiliki oleh pengguna berdasarkan ID mereka.
	 * @param userId ID pengguna.
	 * @throws Exception jika terjadi kesalahan saat mengambil data proyek.
	 */
	public void displayAllProjectDetails(int userId) throws Exception {
		// Mengambil daftar ID proyek yang dimiliki oleh pengguna.
		ArrayList<Integer> projectIds = projectDbController.getProjectIdOwnedByUser(userId);

		// Jika tidak ada proyek yang ditemukan, tampilkan pesan.
		if (projectIds.isEmpty()) {
			System.out.println("Tidak ada proyek yang ditemukan untuk pengguna dengan ID " + userId + "\n");
			return;
		}

		// Iterasi setiap proyek dan tampilkan informasinya.
		for (int projectId : projectIds) {
			Project project = findProjectById(projectId);

			System.out.println("\nProject ID: " + project.getId());
			System.out.println("Title: " + project.getTitle());
			System.out.println("Description: " + project.getDescription());
			System.out.println("Deadline: " + project.getDeadline());

			// Tampilkan informasi spesifik berdasarkan jenis proyek.
			if (project instanceof WebProject) {
				WebProject web = (WebProject) project;
				System.out.println("Domain: " + web.getDomainName());
			} else if (project instanceof MobileProject) {
				MobileProject mob = (MobileProject) project;
				System.out.println("Platform: " + mob.getPlatform());
			} else if (project instanceof DesktopProject) {
				DesktopProject desk = (DesktopProject) project;
				System.out.println("Operating System: " + desk.getOS());
			}
			
			System.out.println("-----------------------------");
		}
	}
        
}
