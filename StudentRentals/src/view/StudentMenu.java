package view;

import data.DataManager;
import model.rentals.Rental;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final DataManager dataManager;
    private final Scanner scanner;
    private List<Rental> available;

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
        List<Rental> available = getAvailableRentals();
        if (available.isEmpty()) {
            System.out.println("No available rentals found.");
            return;
        }

        sortByPrice(available);
        for (Rental r : available) {
            printRentalInfo(r);
        }
    }


    private List<Rental> getAvailableRentals() {
        List<Rental> available = new ArrayList<>();
        for (Rental r : dataManager.rentals) {
            if (r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available")) {
                available.add(r);
            }
        }
        return available;
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
        List<Rental> matches = new ArrayList<>();
        for (Rental r : dataManager.rentals) {
            if (r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available") && r.getPrice() <= maxPrice) {
                matches.add(r);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("No available rentals found.");
            return;
        }

        sortByPrice(matches);
        for (Rental r : matches) {
            printRentalInfo(r);
        }
    }

    private void filterByRentalType() {
        System.out.println("Enter rental type: ");
        String rentalType = scanner.nextLine();
        List<Rental> matches = new ArrayList<>();
        for (Rental r : dataManager.rentals) {
            if (r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available")
                    && r.getClass().getSimpleName().equalsIgnoreCase(rentalType)) {
                matches.add(r);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("No available rentals found.");
            return;
        }

        sortByPrice(matches);
        for (Rental r : matches) {
            printRentalInfo(r);
        }
    }


    private void filterByCity() {
        System.out.println("Enter city: ");
        String city = scanner.nextLine();
        List<Rental> matches = new ArrayList<>();
        for (Rental r : dataManager.rentals) {
            if (r.getStatus() != null && r.getStatus().equalsIgnoreCase("Available")
                    && r.getCity().equalsIgnoreCase(city)) {
                matches.add(r);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("No available rentals found.");
            return;
        }

        sortByPrice(matches);
        for (Rental r : matches) {
            printRentalInfo(r);
        }
    }

    private void bookHome() {
        System.out.println("Book Home");
        System.out.println("Enter your student ID: ");
        String studentID = scanner.nextLine();
        System.out.println("Enter the rental ID: ");
        int rentalID = Integer.parseInt(scanner.nextLine());

        List<Rental> available = getAvailableRentals();
        
        sortByRentalId(available);
        Rental rentalToBook = binarySearchbyId(available, rentalID);

        if (rentalToBook != null) {
            rentalToBook.setStatus("Booked");
            rentalToBook.setStudentId(studentID);
            dataManager.updateRentalsToDB();
            System.out.println("Rental Booked Successfully!!");
        } else {
            System.out.println("rentals not found or already booked.");
        }
    }



    private void sortByRentalId(List<Rental> available) {
        for(int i = 0;i < available.size()-1;i++) {
            int minIndex = i;
            for (int j = i + 1; j < available.size(); j++) {
                if(available.get(j).getRentalID() < available.get(minIndex).getRentalID()) {
                    minIndex = j;
                }
            }
            if(minIndex != i) {
                Rental temp = available.get(i);
                available.set(i, available.get(minIndex));
                available.set(minIndex, temp);
            }
        }
    }

    private void sortByPrice(List<Rental> rentals) {
        for (int i = 0; i < rentals.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < rentals.size(); j++) {
                if (rentals.get(j).getPrice() < rentals.get(minIndex).getPrice()) {
                    minIndex = j;
                }

            }

            if (minIndex != i) {
                Rental temp = rentals.get(i);
                rentals.set(i, rentals.get(minIndex));
                rentals.set(minIndex, temp);
            }
        }
    }

    private Rental binarySearchbyId(List<Rental> rentals, int rentalId) {
        int left = 0;
        int right = rentals.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int midValue = rentals.get(mid).getRentalID();

            if (midValue == rentalId) {
                return rentals.get(mid);
            }
            if (midValue < rentalId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
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
