package model.rentals;

public class Room extends Rental {

    private double capacity;
    private double area;
    private boolean ensuite;

    public Room(double capacity) {
        this.capacity = capacity;
    }

    public Room() {
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public boolean isEnsuite() {
        return ensuite;
    }

    public void setEnsuite(boolean ensuite) {
        this.ensuite = ensuite;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
