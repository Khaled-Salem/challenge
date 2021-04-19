package codefactory.challenge.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String iban;
    private String type;
    private int balance;
    private boolean isLocked = false;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account() {
    }

    public Account(Long id, String iban, String name, String type, int balance, Customer customer) {
        this.id = id;
        this.name = name;
        this.iban = iban;
        this.type = type;
        this.balance = balance;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isLocked() { return isLocked; }

    public void setLocked(boolean locked) { isLocked = locked; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iban='" + iban + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", isLocked=" + isLocked +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getBalance() == account.getBalance() && isLocked() == account.isLocked() && Objects.equals(getId(), account.getId()) && Objects.equals(getName(), account.getName()) && Objects.equals(getIban(), account.getIban()) && Objects.equals(getType(), account.getType()) && Objects.equals(getCustomer(), account.getCustomer());
    }
}
