package model.payment;

import model.rentals.Rental;
import model.user.HomeOwner;
import model.user.Student;
import java.util.List;

public class Payment {
    private float amount;
    private String date;
    private Student student;
    private HomeOwner homeOwner;
    private Rental rental;
    private List<PaymentMethod> paymentMethod;

    public Payment(float amount, String date, Student student,
                   HomeOwner homeOwner, Rental rental,
                   List<PaymentMethod> paymentMethod) {
        this.amount = amount;
        this.date = date;
        this.student = student;
        this.homeOwner = homeOwner;
        this.rental = rental;
        this.paymentMethod = paymentMethod;
    }

    public float totalPayment(){
       float total = 0;
       for (PaymentMethod pm : paymentMethod) {
            total += pm.getAmount();
       }
       return total;
    }
    public void paymentMade(){
        float total = totalPayment();
        if (total != amount) {
            throw(new RuntimeException("Payment failed"));

        }
        for (PaymentMethod pm : paymentMethod) {
            pm.pay();
        }

    }
}

