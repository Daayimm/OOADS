package view;

import data.DataManager;
import java.util.Scanner;

public class MainMenu {
    private DataManager dataManager;
    private Scanner scanner = new Scanner(System.in);
    private StudentMenu studentMenu;
    private GuestMenu guestMenu;
    private HomeOwnerMenu homeOwnerMenu;

    public MainMenu() {
        dataManager = new DataManager();
        studentMenu = new StudentMenu(dataManager, scanner);
        guestMenu = new GuestMenu(dataManager, scanner);
        homeOwnerMenu = new HomeOwnerMenu(dataManager, scanner);
    }

    public void start() {
        boolean running = true;
        dataManager.loadInitialData();
        while (running) {
            System.out.println("\nMain Menu :");
            System.out.println("1. Continue as Student");
            System.out.println("2. Continue as HomeOwner");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    studentMenu.start();
                    break;
                case "2":
                    homeOwnerMenu.start();
                    break;
                case "3":
                    guestMenu.start();
                    break;
                case "4":
                    running = false;
                    scanner.close();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
