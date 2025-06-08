package dpbo.dashboardApp;

import java.util.Scanner;

import dpbo.dashboardApp.auth.AuthService;
import dpbo.dashboardApp.models.User;

public class Main {
    public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Dashboard App!");
		System.out.println("Please enter your username:");
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
		while (choice != 3) {

		}

		input.close();
	}

	public static void displayMenu() {
		System.out.println("1. View Projects");
		System.out.println("2. Create Project");
		System.out.println("3. Exit");
		System.out.print("Enter your choice: ");
	}
 }
