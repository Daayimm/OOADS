package view;

import data.DataManager;
import model.rentals.Rental;
import java.util.Scanner;

public class StudentMenu {
    private final DataManager dataManager;
    private final Scanner scanner;

    public StudentMenu(DataManager dataManager, Scanner scanner) {
        this.dataManager = dataManager;
        this.scanner = scanner;
    }

    public void start() {
        boolean back = false;
        while (!back) {
            System.out.println("\nStudent Menu");
            System.out.println("1. View current Available rentals");
            System.out.println("2. Apply filter");
            System.out.println("3. Book Home");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAvailableRentals();
                    break;
                case "2":
                    studentFilterMenu();
                    break;
                case "3":
                    bookHome();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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

    private void studentFilterMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("Apply Filter");
            System.out.println("1. Filter By Max Price");
            System.out.println("2. Filter by Rental Type");
            System.out.println("3. Filter by City");
            System.out.println("4. Back to Student Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    filterByMaxPrice();
                    break;
                case "2":
                    filterByRentalType();
                    break;
                case "3":
                    filterByCity();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void filterByMaxPrice() {

        System.out.println("Enter max price: ");
        double maxPrice = Double.parseDouble(scanner.nextLine());
        boolean found = false;
        for (Rental r : dataManager.rentals) {
            if(r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available") && r.getPrice() <= maxPrice ) {
                found = true;
                printRentalInfo(r);
            }

        }if (!found){
            System.out.println("No available rentals found.");
        }
    }


    private void filterByRentalType() {

        System.out.println("Enter rental type: ");
        String rentalType = scanner.nextLine();
        boolean found = false;
        for (Rental r : dataManager.rentals) {
            if(r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available") && r.getClass().getSimpleName().equalsIgnoreCase(rentalType)) {
                found = true;
                printRentalInfo(r);
            }
        }if(!found){
            System.out.println("No available rentals found.");
        }
    }

    private void filterByCity() {

        System.out.println("Enter city: ");
        String city = scanner.nextLine();
        boolean found = false;
        for (Rental r : dataManager.rentals) {
            if(r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available") && r.getCity().equalsIgnoreCase(city)) {
                found = true;
                printRentalInfo(r);
            }
        }if(!found){
            System.out.println("No available rentals found.");
        }
    }


    private void bookHome() {
        //TODO: Implement book home functionality
        System.out.println("Book Home");
        System.out.println("Enter your student ID: ");
        String studentID = scanner.nextLine();
        System.out.println("Enter the rental ID: ");
        int rentalID = Integer.parseInt(scanner.nextLine());
        Rental rentalToBook = null;
        for (Rental r : dataManager.rentals) {
            if(r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available") && r.getRentalID() == rentalID) {
                rentalToBook = r;
                break;
            }
        }if (rentalToBook != null) {
            rentalToBook.setStatus("Booked");
            rentalToBook.setStudentId(studentID);
            dataManager.updateRentalsToDB();
            System.out.println("Rental Booked Successfully!!");
            
        }else{
            System.out.println("rentals not found or already booked.");
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
