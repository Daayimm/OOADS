package model.rentals;

public class House extends Rental{
    private double area;

    public House(double area) {
        this.area = area;
    }

    public House() {
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
