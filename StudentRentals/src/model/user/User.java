package model.user;

/**
 * Represents a User in the system
 * This class is part of the model layer of the Design
 * It holds the data and provides getters and setters
 *
 * @author Daayim
 * @version 1.0
 */
public abstract class User {
    private int id;
    private String name;
    private String email;
    private String phone;



    public User(){
    }


    public User(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


