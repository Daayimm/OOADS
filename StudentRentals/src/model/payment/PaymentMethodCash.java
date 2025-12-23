package model.payment;

import model.user.Student;

public class PaymentMethodCash extends PaymentMethod{

    public PaymentMethodCash(Student student, float amount) {
        super(student, amount);
    }

    @Override
    public void pay() {
        System.out.println("Payment by cash   by " + this.getStudent() + " amount: " + this.getAmount());
    }
}
