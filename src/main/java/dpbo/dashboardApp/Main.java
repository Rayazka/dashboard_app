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

public class Main {
    public static void main(String[] args) throws Exception {

		// Initialize the database connection
		DatabaseManager dbManager = new DatabaseManager();
		dbManager.initializeDatabase();

		Scanner input = new Scanner(System.in);
		System.out.println("=========================================");
		System.out.println("  SELAMAT DATANG DI DASHBOARD PROYEK");
		System.out.println("    PT. SAPTALOKA DIGITAL INDONESIA");
        	System.out.println("=========================================\n");
		System.out.print("Please enter your username:");
		String username = input.nextLine();

		System.out.println("Please enter your password:");
		String password = input.nextLine(); // Assuming password is not used in this example, but could be used in AuthService

		AuthService authService = new AuthService();
		if (!authService.login(username)) {
			System.out.println("Login failed. Please check your username and password.");
			input.close();
			return;
		}

		User user = new User(authService.getUserId());
		System.out.println("Welcome, " + user.getUsername() + "! You have successfully logged in.");
		
		int choice = 0;
		while (choice != 9) {
		displayMenu();
		choice = Integer.parseInt(input.nextLine());

		ProjectManager projectManager = new ProjectManager();

		switch (choice) {
			case 1:
				{
					try {
						projectManager.displayAllProjectDetails(user.getId());
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
				break;
			case 2:
				{
					
					System.out.print("Masukkan Judul Proyek: ");
					String title = input.nextLine();
					System.out.print("Masukkan Deskripsi Proyek: ");
					String description = input.nextLine();
					System.out.print("Masukkan Nama Klien: ");
					String client = input.nextLine();
					System.out.print("Masukkan Tanggal Deadline (YYYY-MM-DD): ");
					String deadlineStr = input.nextLine();
					System.out.print("Tipe Proyek (web, desktop, mobile):");
					String type = input.nextLine();
					if (!type.equals("web") && !type.equals("desktop") && !type.equals("mobile")) {
						System.out.println("Tipe proyek tidak valid. Harap masukkan 'web', 'desktop', atau 'mobile'.");
						continue;
					}

					// get clientId from DB
					UserDbController userDbController = new UserDbController();
					int clientId = userDbController.getIdFromUsername(client);

					// Parse the deadline string to LocalDateTime
					LocalDateTime deadline;
					try {
						deadline = LocalDateTime.parse(deadlineStr + "T00:00:00");
					} catch (Exception e) {
						System.out.println("Format tanggal tidak valid. Gunakan format YYYY-MM-DD.");
						continue;
					}

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
					System.out.print("Masukkan ID Proyek yang ingin dicari: ");
					int projectId;
					try {
						projectId = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("ID proyek tidak valid. Harap masukkan angka.");
						continue;
					}

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
					System.out.print("Masukkan Nama Klien untuk mencari proyek: ");
					String clientName = input.nextLine();
					UserDbController userDbController = new UserDbController();
					int clientId;
					try {
						 clientId = userDbController.getIdFromUsername(clientName);
					} catch(ProjectNotFoundException e) {
						System.out.println(e.getMessage());
						continue;
					}

					List<Project> projects = projectManager.findProjectsByClient(clientId);
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
				{
					System.out.print("Masukkan ID Proyek yang ingin dihapus: ");
					int projectId;
					try {
						projectId = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("ID proyek tidak valid. Harap masukkan angka.");
						continue;
					}

					try {
						projectManager.removeProject(projectId);
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
				break;
			case 6:
				revisionMenu();
				break;
			case 9:
				System.out.println("Terima kasih, keluar dari program...");
				break;
			default:
				System.out.println("Pilihan tidak valid. Coba lagi.");
				break;
			}
		}

		input.close();
	}

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

	private static void revisionMenu() throws Exception {
		Scanner scanner = new Scanner(System.in);
		RevisionDbController revisionDbController = new RevisionDbController();
		System.out.print("Masukkan ID proyek: ");
		int projectId;
		try {
			projectId = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("ID proyek tidak valid. Harap masukkan angka.");
			return;
		}

		ProjectManager projectManager = new ProjectManager();

		try {
			Project project;
			try {
				project = projectManager.findProjectById(projectId);
			} catch (ProjectNotFoundException e) {
				System.out.println("Project tidak ditemukan");
				return;
			}

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
					List<Revision> revisi;
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
					if (scanner.hasNextLine()) scanner.nextLine();
					System.out.print("Judul: ");
					String title = scanner.nextLine();
					System.out.print("Deskripsi: ");
					String desc = scanner.nextLine();
					revisionDbController.createRevision(projectId, title, desc);

					System.out.println("Revisi ditambahkan.");
					break;

				case 3:
					if (scanner.hasNextLine()) scanner.nextLine();
					System.out.print("ID Revisi yang ingin diedit: ");
					int idToEdit;
					try {
						idToEdit = Integer.parseInt(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bukan angka");
						return;
					}
					Revision revision = new Revision(projectId);
					try {
						String notes = revisionDbController.getNotes(idToEdit);
						System.out.println("Catatan saat ini: " + notes);
						System.out.print("Masukkan catatan baru: ");
						String newNotes = scanner.nextLine();
						revisionDbController.setNotes(revision.getId(), newNotes);
						System.out.println("Revisi diperbarui.");
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}
				break;

				case 4:
					if (scanner.hasNextLine()) scanner.nextLine();
					System.out.print("ID Revisi yang ingin dihapus: ");
					int idToDelete;
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
					return;

				default:
					System.out.println("Opsi tidak valid.");
					break;
				}
			}
		} catch (ProjectNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}	
 }
