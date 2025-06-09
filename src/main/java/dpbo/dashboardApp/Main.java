package dpbo.dashboardApp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dpbo.dashboardApp.auth.AuthService;
import dpbo.dashboardApp.db.DatabaseManager;
import dpbo.dashboardApp.db.ProjectDbController;
import dpbo.dashboardApp.db.RevisionDbController;
import dpbo.dashboardApp.db.UserDbController;
import dpbo.dashboardApp.exceptions.ProjectNotFoundException;
import dpbo.dashboardApp.models.Project;
import dpbo.dashboardApp.models.ProjectManager;
import dpbo.dashboardApp.models.Revision;
import dpbo.dashboardApp.models.User;

/**
 * Entry point untuk aplikasi Dashboard Proyek.
 * 
 * Kelas ini bertanggung jawab untuk:
 * 1. Menginisialisasi koneksi database
 * 2. Mengelola proses login dengan input dari pengguna.
 * 3. Menampilkan menu utama dan menangani interaksi pengguna untuk 
 * 4. menjalankan fitur-fitur seperti pembuatan proyek, 
 * 5. pencarian, penghapusan, dan pengelolaan revisi.
 */
public class Main {
    public static void main(String[] args) throws Exception {

		// Initialize the database connection
		DatabaseManager dbManager = new DatabaseManager();
		dbManager.initializeDatabase();

		// Membuat scanner untuk input pengguna
		Scanner input = new Scanner(System.in);
		System.out.println("=========================================");
		System.out.println("  SELAMAT DATANG DI DASHBOARD PROYEK");
		System.out.println("    PT. SAPTALOKA DIGITAL INDONESIA");
        	System.out.println("=========================================\n");

		AuthService authService = new AuthService();
		boolean isLoginValid = false;

		while (!isLoginValid) {
		System.out.print("Please enter your username: ");
		String username = input.nextLine();

		System.out.print("Please enter your password: ");
		String password = input.nextLine(); // belum digunakan

		if (authService.login(username)) {
			isLoginValid = true;
			User user = new User(authService.getUserId());
			System.out.println("Welcome, " + user.getUsername() + "! You have successfully logged in.\n");
			// lanjut ke menu utama...
		} else {
			System.out.println("Login failed. Username tidak ditemukan. Silakan coba lagi.\n");
		}
		}

		// Mendapatkan data User berdasarkan ID yang diberikan oleh AuthService
		User user = new User(authService.getUserId());
		
		// Loop utama untuk menampilkan menu dan mengeksekusi perintah sesuai pilihan pengguna
		int choice = 0;
		while (choice != 9) {
			// Menampilkan menu utama
			displayMenu();

			// Validasi input yang diberikan adalah angka
			 try {
				choice = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Input tidak valid. Harap masukkan angka.");
				continue; // kembali ke awal while-loop tanpa menghentikan program
			}

			// Membuat objek ProjectManager untuk mengelola proyek
			ProjectManager projectManager = new ProjectManager();

			// Menangani pilihan menu yang diberikan oleh pengguna
			switch (choice) {
				case 1:
					// Menampilkan semua detail proyek milik pengguna
					try {
						projectManager.displayAllProjectDetails(user.getId());
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
					break;
				case 2:
				{
					// Menambahkan proyek baru
					System.out.print("Masukkan Judul Proyek: ");
					String title = input.nextLine();
					System.out.print("Masukkan Deskripsi Proyek: ");
					String description = input.nextLine();
					System.out.print("Masukkan Nama Klien: ");
					String client = input.nextLine();
					System.out.print("Masukkan Tanggal Deadline (YYYY-MM-DD): ");
					String deadlineStr = input.nextLine();
					System.out.print("Tipe Proyek (web, desktop, mobile): ");
					String type = input.nextLine();
					
					// Validasi tipe proyek
					if (!type.equals("web") && !type.equals("desktop") && !type.equals("mobile")) {
						System.out.println("Tipe proyek tidak valid. Harap masukkan 'web', 'desktop', atau 'mobile'.");
						continue;
					}

					// get clientId from DB
					int clientId = -1;
					try {
						UserDbController userDbController = new UserDbController();
						clientId = userDbController.getIdFromUsername(client);
					} catch (Exception e) {
						System.out.println("\nKlien tidak ditemukan. Pastikan nama klien sudah benar.\n");
						break;
					}
					

					// Parse the deadline string to LocalDateTime
					LocalDateTime deadline;
					try {
						deadline = LocalDateTime.parse(deadlineStr);
					} catch (Exception e) {
						System.out.println("Format tanggal tidak valid. Gunakan format YYYY-MM-DD.");
						continue;
					}

					// Membuat proyek baru melalui ProjectDbController
					ProjectDbController projectDbController = new ProjectDbController();
					try {
						projectDbController.createNewProject(title, description, deadline, clientId, type);
						System.out.println("Proyek berhasil ditambahkan.");
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
					break;
				case 3:
				{
					// Mencari proyek berdasarkan ID
					System.out.print("Masukkan ID Proyek yang ingin dicari: ");
					int projectId;
					/**
					 * Validasi input ID proyek
					 * Menggunakan try-catch untuk menangani input yang tidak valid
					 */
					try {
						projectId = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("ID proyek tidak valid. Harap masukkan angka.");
						continue;
					}
					
					/**
					 * Mencari proyek berdasarkan ID yang diberikan
					 * Menggunakan ProjectManager untuk menemukan proyek
					 */
					try {
						Project project = projectManager.findProjectById(projectId);
						System.out.println("Proyek ditemukan: " + project.toString());
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
					break;
				case 4:
				{
					// Mencari proyek berdasarkan nama klien
					System.out.print("Masukkan Nama Klien untuk mencari proyek: ");
					// Membaca nama klien dari input
					String clientName = input.nextLine();
					// Validasi input nama klien
					UserDbController userDbController = new UserDbController();
					int clientId;
					/**
					 * Mendapatkan ID klien berdasarkan nama klien
					 * Menggunakan UserDbController untuk mendapatkan ID klien
					 * Jika klien tidak ditemukan, akan menampilkan pesan kesalahan
					 */
					try {
						clientId = userDbController.getIdFromUsername(clientName);
					} catch(ProjectNotFoundException e) {
						System.out.println(e.getMessage());
						continue;
					}

					// Mencari proyek berdasarkan ID klien
					List<Project> projects = projectManager.findProjectsByClient(clientId);
					/**
					 * Jika tidak ada proyek ditemukan, tampilkan pesan yang sesuai
					 * Jika ada proyek ditemukan, tampilkan daftar proyek
					 */
					if (projects.isEmpty()) {
						System.out.println("Tidak ada proyek ditemukan untuk klien: " + clientName);
					} else {
						System.out.println("Proyek yang ditemukan untuk klien " + clientName + ":");
						for (Project project : projects) {
							System.out.println("- " + project.toString());
						}
					}
				}
					break;
				case 5:
				// Menghapus proyek berdasarkan ID
				{
					System.out.print("Masukkan ID Proyek yang ingin dihapus: ");
					int projectId = 0;
					/**
					 * Validasi input ID proyek
					 * Menggunakan try-catch untuk menangani input yang tidak valid
					 */
					try {
						projectId = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("ID proyek tidak valid. Harap masukkan angka.");
						continue;
					}

					/**
					 * Menghapus proyek berdasarkan ID yang diberikan
					 * Menggunakan ProjectManager untuk menghapus proyek
					 * Jika proyek tidak ditemukan, akan menampilkan pesan kesalahan
					 */
					try {
						projectManager.removeProject(projectId);
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
					break;
				case 6:
					// Menampilkan menu revisi
					revisionMenu();
					break;
				case 9:
					// Keluar dari program
					System.out.println("Terima kasih, keluar dari program...");
					break;
				default:
					// Menangani pilihan yang tidak valid
					System.out.println("Pilihan tidak valid. Coba lagi.");
					break;
			}
		}
		input.close();
	}

	/**
	 * Menampilkan menu utama untuk dashboard klien.
	 */
	public static void displayMenu() {
		System.out.println("=========================================");
		System.out.println("           DASHBOARD UTAMA KLIEN");
		System.out.println("=========================================");
		System.out.println("1. Lihat semua proyek");
		System.out.println("2. Tambah proyek baru");
		System.out.println("3. Cari proyek berdasarkan ID");
		System.out.println("4. Cari proyek berdasarkan nama klien");
		System.out.println("5. Hapus proyek");
		System.out.println("6. Menu Revisi");
		System.out.println("9. Keluar");
		System.out.print("Enter your choice: ");
	}

	/**
	 * Menampilkan menu revisi untuk proyek tertentu.
	 * 
	 * @throws Exception jika terjadi kesalahan saat mengakses database atau operasi lainnya.
	 */
	private static void revisionMenu() throws Exception {
		Scanner scanner = new Scanner(System.in);
		RevisionDbController revisionDbController = new RevisionDbController();

		// Meminta input ID proyek
		System.out.print("Masukkan ID proyek: ");
		int projectId;
		try {
			projectId = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("ID proyek tidak valid. Harap masukkan angka.");
			return;
		}

        	// Membuat instance ProjectManager untuk mendapatkan data proyek
		ProjectManager projectManager = new ProjectManager();

		/**
		 * Blok try ini mencakup keseluruhan logika menu revisi.
    		 * Kalau ada error tak terduga (misalnya error input atau lain-lain) yang belum tertangani secara spesifik,
		 */
		try {
			// Mencari proyek berdasarkan ID
			Project project;

			/**
			 * Menangani error khusus ketika mencari proyek.
			 */
			try {
				project = projectManager.findProjectById(projectId);
			} catch (ProjectNotFoundException e) {
				System.out.println("Project tidak ditemukan");
				return;
			}

			// Menampilkan menu Revisi
			int choice = 0;
			while (choice != 5) {
			System.out.println("\n=== MENU REVISI UNTUK PROYEK: " + project.getTitle() + " ===");
			System.out.println("1. Lihat semua revisi");
			System.out.println("2. Tambah revisi");
			System.out.println("3. Edit revisi");
			System.out.println("4. Hapus revisi");
			System.out.println("5. Kembali");

			System.out.print("Pilih opsi: ");
			int pilihan = scanner.nextInt();

			switch (pilihan) {
				case 1:
					// Menampilkan semua revisi untuk proyek yang dipilih.
					List<Revision> revisi;
                			
					// Mendapatkan daftar ID revisi dari database controller.					
					ArrayList<Integer> ids =  new RevisionDbController().getRevisionIdsFromProject(projectId);
					revisi = new ArrayList<Revision>();
					for (int id : ids) {
						revisi.add(new Revision(id));
					}

					if (revisi.isEmpty()) {
						System.out.println("Belum ada revisi.");
					} else {
						for (Revision r : revisi) {
						System.out.println("- " + r.toString());
						}
					}
					break;

				case 2:
					// Menambahkan revisi baru.
					if (scanner.hasNextLine()) scanner.nextLine();

					System.out.print("\nCatatan: ");
					String desc = scanner.nextLine();

					// Memanggil RevisionDbController untuk membuat revisi baru
					revisionDbController.createRevision(projectId, "Revisi", desc);

					System.out.println("Revisi ditambahkan.");
					break;

				case 3:
					// Mengedit revisi yang sudah ada.
					if (scanner.hasNextLine()) scanner.nextLine();

					// Meminta input ID revisi yang ingin diedit
					System.out.print("\nID Revisi yang ingin diedit: ");
					int idToEdit;
					/**
					 * Validasi input ID revisi
					 * Menggunakan try-catch untuk menangani input yang tidak valid
					 */
					try {
						idToEdit = Integer.parseInt(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bukan angka");
						return;
					}

					// Membuat objek Revision untuk mendapatkan informasi revisi yang ingin diedit
					Revision revision = new Revision(projectId);

					/**
					 * Menangani error khusus ketika mendapatkan revisi berdasarkan ID
					 * Jika revisi tidak ditemukan, akan menampilkan pesan kesalahan
					 */
					try {
						String notes = revisionDbController.getNotes(idToEdit);
						
						System.out.println("\nCatatan saat ini: " + notes);
						System.out.print("Masukkan catatan baru: ");
						// Membaca catatan baru dari input
						String newNotes = scanner.nextLine();

						// Memperbarui catatan revisi di database
						revisionDbController.setNotes(revision.getId(), newNotes);
						System.out.println("Revisi diperbarui.");
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				break;

				case 4:
					// Menghapus revisi berdasarkan ID
					if (scanner.hasNextLine()) scanner.nextLine();

					// Meminta input ID revisi yang ingin dihapus
					System.out.print("\nID Revisi yang ingin dihapus: ");
					int idToDelete;

					/**
					 * Validasi input ID revisi
					 * Menggunakan try-catch untuk menangani input yang tidak valid	
					 */
					try {
						idToDelete = Integer.parseInt(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bukan angka");
						return;
					}
					try {
						revisionDbController.removeRevision(idToDelete);
						System.out.println("Revisi berhasil dihapus.");
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
					break;
				
				case 5:
					// Kembali ke menu utama
					return;

				default:
					// Menangani pilihan yang tidak valid
					System.out.println("Opsi tidak valid.");
					break;
				}
			}
		} catch (ProjectNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}	
 }
