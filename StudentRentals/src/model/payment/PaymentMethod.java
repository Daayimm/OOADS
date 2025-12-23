package model.payment;

import model.user.Student;

public abstract class PaymentMethod {
    private Student student;
    private float amount;

    public PaymentMethod(Student student, float amount) {
        this.student = student;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public Student getStudent() {
        return student;
    }

    public abstract void pay();
}
