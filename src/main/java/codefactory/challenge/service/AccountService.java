package codefactory.challenge.service;

import codefactory.challenge.model.Account;
import codefactory.challenge.model.Customer;
import codefactory.challenge.model.TransactionHistory;
import codefactory.challenge.repo.AccountRepository;
import codefactory.challenge.repo.TransactionHistoryRepository;
import codefactory.challenge.service.structs.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    public static final String CHECKING = "Checking";
    public static final String SAVINGS = "Savings";
    public static final String PRIVATE_LOAN = "Private Loan";


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;


    public Object[] getPossibleAccounts(Customer customer) {
        ArrayList<String> possibleAccounts = new ArrayList<>(Arrays.asList(CHECKING, SAVINGS, PRIVATE_LOAN));
        accountRepository.findByCustomerId(customer.getId()).forEach(account -> {
            possibleAccounts.remove(account.getType());
        });

        return possibleAccounts.toArray();
    }

    public void populateAccount(Account account) {
        account.setIban(this.generateIBAN("DE"));
        accountRepository.save(account);

        TransactionHistory transactionHistory = new TransactionHistory(account, "Initial Deposit", account.getBalance());

        transactionHistoryRepository.save(transactionHistory);
    }

    public String generateIBAN(String countryCode) {
        StringBuilder iban = new StringBuilder(countryCode);
        for (int i = 0; i < 18; i++)
            iban.append(ThreadLocalRandom.current().nextInt(0, 10));

        return iban.toString();
    }

    public List<Account> getAllAccountsByCustomerId(Long id) {
        return accountRepository.findByCustomerId(id);
    }

    public Account getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);

        return account.orElse(null);

    }

    public void deposit(Deposit deposit) {
        Account account = this.getAccountById(deposit.getAccountId());
        account.setBalance(account.getBalance() + deposit.getAmount());
        accountRepository.save(account);

        TransactionHistory transactionHistory = new TransactionHistory(account, "Deposit", deposit.getAmount());
        transactionHistoryRepository.save(transactionHistory);
    }

    public List<Account> getPossibleTransfers(Account account, Customer customer) {
        ArrayList<Account> possibleAccounts = new ArrayList<>();

        switch (account.getType()) {
            case CHECKING:
                List<Account> savingsAccount = accountRepository.findByTypeAndCustomerId(SAVINGS, customer.getId());
                if (savingsAccount.size() > 0)
                    possibleAccounts.addAll(savingsAccount);

                List<Account> privateLoanAccounts = accountRepository.findByTypeAndCustomerId(PRIVATE_LOAN, customer.getId());
                if (privateLoanAccounts.size() > 0)
                    possibleAccounts.addAll(privateLoanAccounts);

                break;
            case SAVINGS:
                List<Account> checkingAccounts = accountRepository.findByTypeAndCustomerId(CHECKING, customer.getId());
                if (checkingAccounts.size() > 0)
                    possibleAccounts.addAll(checkingAccounts);

                break;
        }

        return possibleAccounts;
    }

    public void transferMoney(Account fromAccount, Account toAccount, int amount) {
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        TransactionHistory fromAccountTransaction = new TransactionHistory(fromAccount, "Transfer", amount * -1);
        TransactionHistory toAccountTransaction = new TransactionHistory(toAccount, "Transfer", amount);

        transactionHistoryRepository.save(fromAccountTransaction);
        transactionHistoryRepository.save(toAccountTransaction);
    }

    public void triggerLock(Long accountId) {
        Account account = this.getAccountById(accountId);
        String action = account.isLocked() ? "Unlock" : "Lock";
        TransactionHistory transactionHistory = new TransactionHistory(account, action);

        account.setLocked(!account.isLocked());

        accountRepository.save(account);
        transactionHistoryRepository.save(transactionHistory);
    }

    public List<TransactionHistory> getHistory(Long accountId) {
        return transactionHistoryRepository.findByAccountId(accountId);
    }
}
