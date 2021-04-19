package codefactory.challenge.service.structs;

public class Deposit {

    private Long accountId;
    private String iban;
    private int amount = 0;

    public Deposit() {}

    public Deposit(Long accountId, String iban) {
        this.accountId = accountId;
        this.iban = iban;
    }

    public Long getAccountId() { return accountId; }

    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
