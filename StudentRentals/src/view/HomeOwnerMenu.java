package view;

import data.DataManager;
import exceptions.CSVException;
import model.rentals.Rental;
import model.user.HomeOwner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HomeOwnerMenu {
    private final DataManager dataManager;
    private final Scanner scanner;

    public HomeOwnerMenu(DataManager dataManager, Scanner scanner) {
        this.dataManager = dataManager;
        this.scanner = scanner;
    }

    public void start() {
        boolean back = false;
        while (!back) {
            System.out.println("\nHomeOwner Menu");
            System.out.println("1. Add a Home");
            System.out.println("2. View Your Home");
            System.out.println("3. Rent Home");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addHome();
                    break;
                case "2":
                    viewYourHomes();
                    break;
                case "3":
                    rentHome();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addHome() {
        System.out.println("\nAdd a Home");
        System.out.println("Enter your Home owner ID: ");
        int homeOwnerId = Integer.parseInt(scanner.nextLine());
        // validating homeOwner ID
        HomeOwner owner = dataManager.homeowners.stream().filter(h -> h.getId() == homeOwnerId).findFirst().orElse(null);
        if (owner == null) {
            System.out.println("HomeOwner not found.");
            return;
        }

        System.out.println("Enter your rental type(House,Apartment,Room): ");
        String rentalType = scanner.nextLine();
        System.out.println("Enter rent: ");
        double rent = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter your property status(Booked/Available):  ");
        String propertyStatus = scanner.nextLine();
        System.out.println("Enter your property address: ");
        String propertyAddress = scanner.nextLine();
        System.out.println("Enter property city: ");
        String propertyCity = scanner.nextLine();

        // chatGPT
        // making a real object based on user choice
        int newRentalId = dataManager.rentals.stream().mapToInt(r -> r.getRentalID()).max().orElse(0) + 1;

        Rental rental = null;
        if (rentalType.equalsIgnoreCase("Apartment")) rental = new model.rentals.Apartment();
        else if (rentalType.equalsIgnoreCase("House")) rental = new model.rentals.House();
        else if (rentalType.equalsIgnoreCase("Room")) rental = new model.rentals.Room();
        if (rental == null) {
            System.out.println("Invalid rental type.");
            return;
        }
        rental.setRentalID(newRentalId);
        rental.setStatus(propertyStatus);
        rental.setAddress(propertyAddress);
        rental.setCity(propertyCity);
        HomeOwner homeOwner = dataManager.homeowners.stream().filter(h -> h.getId() == homeOwnerId).findFirst().orElse(null);
        rental.setHomeOwner(homeOwner);
        rental.setPrice(rent);
        dataManager.rentals.add(rental);
        // Append to Rental.csv
        try (FileWriter fw = new FileWriter("StudentRentals/Rental.csv", true)) {
            fw.write(String.format("%d,%d,%s,%s,%.2f,%s,%s,%s\n",
                    newRentalId, homeOwnerId,"", rentalType, rent, propertyStatus,propertyCity, propertyAddress));
        } catch (IOException e) {
            throw new CSVException("An exception occured while updating into CSV",e);
        }
        System.out.println("Home added successfully.");
    }

    private void viewYourHomes() {
        System.out.println("Enter your home owner ID: ");
        int homeId = Integer.parseInt(scanner.nextLine());
        boolean found = false;
        for(Rental rental : dataManager.rentals){
            if(rental.getHomeOwner() != null && rental.getHomeOwner().getId() == homeId){
                found = true;
                printRentalInfo(rental);
            }
        }if(!found){
            System.out.println("No homes found for this home owner.");
        }


    }

    private void rentHome() {
        System.out.println("Enter your home owner ID: ");
        int homeId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter tenants student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter rental property ID: ");
        int propertyId = Integer.parseInt(scanner.nextLine());
        Rental rentalToBook = null;
        for(Rental rental : dataManager.rentals){
            if(rental.getHomeOwner() != null && rental.getHomeOwner().getId() == homeId &&
                    rental.getRentalID() == propertyId &&
                    rental.getStatus().equalsIgnoreCase("Available")){
                rentalToBook = rental;
                break;
            }
        }if(rentalToBook == null){
                System.out.println("Rental not found or already booked");
                return;
            }else{
            rentalToBook.setStudentId(String.valueOf(studentId));
            rentalToBook.setStatus("Booked");
            dataManager.updateRentalsToDB();
            System.out.println("Rental booked successfully!!");

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
