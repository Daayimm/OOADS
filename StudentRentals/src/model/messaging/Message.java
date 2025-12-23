package model.messaging;
import model.user.User;

/**
 * This method is used for communication between users of the system
 */


public class Message {

    private int id;
    private User sender;
    private User recipient;
    private String message;


    public Message(int id,User recipient, String message, User sender) {
        this.recipient = recipient;
        this.message = message;
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
