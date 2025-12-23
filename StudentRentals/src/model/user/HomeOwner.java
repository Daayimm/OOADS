package model.user;



import model.rentals.Apartment;
import model.rentals.Rental;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;

public class HomeOwner extends User {
    ArrayList<Rental> rentals = new ArrayList<>();

    public HomeOwner(int id, String name, String email, String phone) {

        super(id, name, email, phone);
    }

    public HomeOwner() {
    }

    public void addRental(Rental rental) {

        rentals.add(rental);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
    }


    public static void main(String[] args) {
        Rental r = new Apartment();
         HomeOwner homeOwner1 = new HomeOwner (1,"h","@gmail.com",
                 "+97430546086");

    }
}
