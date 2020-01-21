package dao.models;

import javax.persistence.*;

@Entity
@Table(name = "email_addresses")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String value;

    public Email() {
    }

    public Email(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
