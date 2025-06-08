package dpbo.dashboardApp;

import java.util.Scanner;

import dpbo.dashboardApp.auth.AuthService;
import dpbo.dashboardApp.models.User;

public class Main {
    public static void main(String[] args) throws Exception {
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
		while (choice != 0) {
		displayMenu();
		choice = Integer.parseInt(input.nextLine());

		switch (choice) {
			case 1:
				projectManager.displayAllProjectDetails();
				pause();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				revisionMenu();
				break;
			case 0:
				System.out.println("Terima kasih, keluar dari program...");
				break;
			default:
				System.out.println("Pilihan tidak valid. Coba lagi.");
				break();
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
		System.out.println("0. Keluar");
		System.out.print("Enter your choice: ");
	}

	private static void revisionMenu() {
		System.out.print("Masukkan ID proyek: ");
		String projectId = scanner.nextLine();

		try {
			Project project = projectManager.findProjectById(projectId);

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
					List<Revision> revisi = project.getAllRevisions();
					if (revisi.isEmpty()) {
						System.out.println("Belum ada revisi.");
					} else {
						for (Revision r : revisi) {
						System.out.println("- " + r.toString());
						}
					}
					break;

				case 2:
					System.out.print("ID Revisi: ");
					String id = scanner.nextLine();
					System.out.print("Judul: ");
					String title = scanner.nextLine();
					System.out.print("Deskripsi: ");
					String desc = scanner.nextLine();

					Revision newRev = new Revision(id, title, desc);
					project.addRevision(id, newRev);
					System.out.println("Revisi ditambahkan.");
					break;

				case 3:
					System.out.print("ID Revisi yang ingin diedit: ");
					String idToEdit = scanner.nextLine();
					Revision editRev = project.getRevisionByKey(idToEdit);
					if (editRev != null) {
						System.out.print("Judul baru: ");
						editRev.setTitle(scanner.nextLine());
						System.out.print("Deskripsi baru: ");
						editRev.setDescription(scanner.nextLine());
						System.out.print("Status baru: ");
						editRev.setStatus(scanner.nextLine());
						boolean updated = project.updateRevision(idToEdit, editRev);
						if (updated) {
						System.out.println("Revisi diperbarui.");
						} else {
						System.out.println("Gagal memperbarui revisi.");
						}
					} else {
						System.out.println("Revisi tidak ditemukan.");
					}
					break;

				case 4:
					System.out.print("ID Revisi yang ingin dihapus: ");
					String idToDelete = scanner.nextLine();
					boolean removed = project.deleteRevision(idToDelete);
					if (removed) {
						System.out.println("Revisi dihapus.");
					} else {
						System.out.println("Revisi tidak ditemukan atau gagal dihapus.");
					}
					break;

					case 5:
					back = true;
					break;

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
