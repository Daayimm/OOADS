package model.payment;

import model.user.Student;

public class PaymentMethodTransfer extends PaymentMethod{

    public PaymentMethodTransfer(Student student, float amount) {
        super(student, amount);
    }

    @Override
    public void pay() {
        System.out.println("Payment through Bank transfer by " + this.getStudent() + " by " + this.getAmount());
    }
}
