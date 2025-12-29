package data;

import model.user.HomeOwner;
import model.rentals.Rental;
import model.user.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    // Singleton instance - thread-safe eager initialization
    private static final DataManager instance = new DataManager();

    public List<HomeOwner> homeowners = new ArrayList<>();
    public List<Rental> rentals = new ArrayList<>();
    public List<Student> students = new ArrayList<>();

    private DataManager() {
// Private constructor ensures no external instantiation

    }

    // Public method to get the singleton instance
    public static DataManager getInstance() {
        return instance;
    }



    public void loadInitialData() {
        loadHomeOwners();
        loadRentals();
        loadStudents();
    }

    private void loadHomeOwners() {
        String csvFile = "StudentRentals/HomeOwners.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    HomeOwner ho = new HomeOwner(
                        Integer.parseInt(data[0].trim()),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim()
                    );
                    homeowners.add(ho);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRentals() {
        String csvFile = "StudentRentals/Rental.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    int rentalId = Integer.parseInt(data[0].trim());
                    int homeownerId = Integer.parseInt(data[1].trim());
                    String studentId = data[2].trim(); // new column
                    String rentalType = data[3].trim();
                    double price = Double.parseDouble(data[4].trim());
                    String status = data[5].trim();
                    // Find the HomeOwner object by ID
                    HomeOwner ho = null;
                    for (HomeOwner h : homeowners) {
                        if (h != null && h.getId() == homeownerId) {
                            ho = h;
                            break;
                        }
                    }
                    Rental rental = null;
                    if (rentalType.equalsIgnoreCase("Apartment")) {
                        rental = new model.rentals.Apartment();
                    } else if (rentalType.equalsIgnoreCase("House")) {
                        rental = new model.rentals.House();
                    } else if (rentalType.equalsIgnoreCase("Room")) {
                        rental = new model.rentals.Room();
                    }
                    if (rental != null) {
                        rental.setRentalID(rentalId);
                        rental.setHomeOwner(ho);
                        rental.setStudentId(studentId); // set studentId
                        rental.setPrice(price);
                        rental.setStatus(status);
                        rental.setCity(data[6].trim()); // city column
                        rental.setAddress(data[7].trim()); // address column
                        rentals.add(rental);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRentalsToDB() {
        String csvFile = "StudentRentals/Rental.csv";
        try (FileWriter fw = new FileWriter(csvFile)) {
            // Write header
            fw.write("rental_id,homeowner_id,student_id,rental_type,price,status,city,address\n");
            for (Rental r : rentals) {
                String rentalType = r.getClass().getSimpleName();
                int homeownerId = r.getHomeOwner() != null ? r.getHomeOwner().getId() : -1;
                fw.write(String.format("%d,%d,%s,%s,%.2f,%s,...,%s,%s\n",
                    r.getRentalID(),
                    homeownerId,
                    r.getStudentId(),
                    rentalType,
                    r.getPrice(),
                    r.getStatus(),
                    r.getCity(),
                    r.getAddress()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        String csvFile = "StudentRentals/Students.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Student student = new Student(
                        Integer.parseInt(data[0].trim()), // id
                        data[1].trim(), // name
                        data[3].trim(), // email
                        data[4].trim(), // phone
                        data[2].trim()  // university
                    );
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
