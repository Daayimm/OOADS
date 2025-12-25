package view;

import data.DataManager;
import model.user.Student;
import model.user.HomeOwner;
import model.rentals.Rental;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GuestMenu {
    private final DataManager dataManager;
    private final Scanner scanner;

    public GuestMenu(DataManager dataManager, Scanner scanner) {
        this.dataManager = dataManager;
        this.scanner = scanner;
    }

    public void start() {
        boolean back = false;
        while (!back) {
            System.out.println("\nGuest Menu");
            System.out.println("1. Register as Student");
            System.out.println("2. Register as Home Owner");
            System.out.println("3. View current Available rentals");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    registerStudent();
                    break;
                case "2":
                    registerHomeOwner();
                    break;
                case "3":
                    viewAvailableRentals();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerStudent() {
        try {
            System.out.println("\n--- Register as Student ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter university: ");
            String university = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            int newId = dataManager.students.stream().mapToInt(s -> s.getId()).max().orElse(0) + 1;
            Student student = new Student(newId, name, email, phone, university);
            // adding data to current running the application
            dataManager.students.add(student);
            // Append to Students.csv
            try (FileWriter fw = new FileWriter("StudentRentals/Students.csv", true)) {
                // storing data to database(csv)
                fw.write(String.format("%d,%s,%s,%s,%s,\n", newId, name, university, email, phone));
            }
            System.out.println("Student registered successfully with id : " + newId);
        } catch (IOException e) {
            System.out.println("Error registering student: " + e.getMessage());
        }
    }

    private void registerHomeOwner() {
        try {
            System.out.println("\n--- Register as Home Owner ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            int newId = dataManager.homeowners.stream().mapToInt(h -> h.getId()).max().orElse(0) + 1;
            HomeOwner ho = new HomeOwner(newId, name, email, phone);
            dataManager.homeowners.add(ho);
            // Append to HomeOwners.csv
            try (FileWriter fw = new FileWriter("StudentRentals/HomeOwners.csv", true)) {
                fw.write(String.format("%d,%s,%s,%s\n", newId, name, email, phone));
            }
            System.out.println("Home Owner registered successfully with id : " + newId);
        } catch (IOException e) {
            System.out.println("Error registering home owner: " + e.getMessage());
        }
    }

    private void viewAvailableRentals() {
        System.out.println("\n--- Available Rentals ---");
        boolean found = false;
        for (Rental r : dataManager.rentals) {
            if (r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available")) {
                found = true;
                printRentalInfo(r);
            }
        }
        if (!found) {
            System.out.println("No available rentals found.");
        }
    }

    private void printRentalInfo(Rental r) {
        String type = r.getClass().getSimpleName();
        String city = r.getCity();
        String address = r.getAddress();
        double price = r.getPrice();
        String owner = (r.getHomeOwner() != null) ? r.getHomeOwner().getName() : "N/A";
        System.out.printf("ID: %d | Type: %s | City: %s | Address: %s | Price: %.2f | Owner: %s\n",
                r.getRentalID(), type, city, address, price, owner);
    }
}
