package model.rentals;

public class Apartment extends Rental{

    private String typeOfApartment;

    public Apartment(String typeOfApartment) {
        this.typeOfApartment = typeOfApartment;
    }

    public Apartment() {
    }

    public String getTypeOfApartment() {
        return typeOfApartment;
    }

    public void setTypeOfApartment(String typeOfApartment) {
        this.typeOfApartment = typeOfApartment;
    }
}
