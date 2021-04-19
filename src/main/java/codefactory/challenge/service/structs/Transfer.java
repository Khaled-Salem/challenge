package codefactory.challenge.service.structs;

import codefactory.challenge.model.Account;

import java.util.List;

public class Transfer {

    private Long accountId;
    private List<Account> possibleAccountsTo;
    private String iban;
    private int amount = 0;


    public Transfer() {}

    public Transfer(Long accountId, List<Account> possibleAccountsTo, String iban) {
        this.accountId = accountId;
        this.possibleAccountsTo = possibleAccountsTo;
        this.iban = iban;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<Account> getPossibleAccountsTo() {
        return possibleAccountsTo;
    }

    public void setPossibleAccountsTo(List<Account> possibleAccountsTo) {
        this.possibleAccountsTo = possibleAccountsTo;
    }

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
