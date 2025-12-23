package model.user;

public class Student extends User {
    private String university;

    public Student() {
    }

    public Student(int id, String name, String email, String phone, String university) {
        super(id, name, email, phone);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
