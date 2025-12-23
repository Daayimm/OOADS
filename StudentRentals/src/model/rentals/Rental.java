package model.rentals;

import model.user.HomeOwner;

public abstract class Rental {
    private String address;
    private String city;
    private HomeOwner homeOwner;
    private int rentalID;
    private String status; // Available, Booked
    private double price;
    private String studentId; // <-- Added field
    //add available_date,booked_date
    private String availableDate;
    private String bookedDate;

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public HomeOwner getHomeOwner() {
        return homeOwner;
    }
    public void setHomeOwner(HomeOwner homeOwner) {
        this.homeOwner = homeOwner;
    }
    public int getRentalID() {
        return rentalID;
    }
    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }
}
