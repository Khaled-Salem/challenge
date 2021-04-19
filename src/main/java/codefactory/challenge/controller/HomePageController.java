package codefactory.challenge.controller;

import codefactory.challenge.model.Account;
import codefactory.challenge.model.Customer;
import codefactory.challenge.model.TransactionHistory;
import codefactory.challenge.service.AccountService;
import codefactory.challenge.service.CustomerService;
import codefactory.challenge.service.structs.Deposit;
import codefactory.challenge.service.structs.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @GetMapping("/index")
    public String index(Model model) throws Exception
    {
        Customer customer = customerService.getCustomerById(Integer.toUnsignedLong(1));

        model.addAttribute("user", customer);
        model.addAttribute("accounts", accountService.getAllAccountsByCustomerId(customer.getId()));

        return "index";
    }

    @GetMapping("/deposit")
    public String depositMoneyPage(Model model, @RequestParam(name="iban") String iban, @RequestParam(name="id") Long id) throws Exception
    {
        Customer customer = customerService.getCustomerById(Integer.toUnsignedLong(1));
        Deposit depositObject = new Deposit(id, iban);

        model.addAttribute("user", customer);
        model.addAttribute("depositObj", depositObject);

        return "deposit";
    }

    @PostMapping("depositMoney")
    public String deposit(Deposit deposit)
    {
        accountService.deposit(deposit);
        return "redirect:/index";
    }


    @GetMapping("/transfer")
    public String transferMoneyPage(Model model, @RequestParam(name="ibanFrom") String ibanFrom, @RequestParam(name="id") Long id) throws Exception
    {
        Customer customer = customerService.getCustomerById(Integer.toUnsignedLong(1));
        Account account = accountService.getAccountById(id);

        List<Account> accountsPossible = accountService.getPossibleTransfers(account, customer);
        Transfer transferObj = new Transfer(id, accountsPossible, ibanFrom);

        model.addAttribute("user", customer);
        model.addAttribute("transferObj", transferObj);
        model.addAttribute("maxAmount", account.getBalance());

        return "transfer";
    }

    @PostMapping("/transferMoney")
    public String transferMoney(Transfer transfer)
    {
        Account fromAccount = accountService.getAccountById(transfer.getAccountId());
        Account toAccount = accountService.getAccountById(transfer.getPossibleAccountsTo().get(0).getId());
        accountService.transferMoney(fromAccount, toAccount, transfer.getAmount());

        return "redirect:/index";
    }

    @PostMapping("/secureCard")
    public String lockUnlockAccount(@RequestParam Long accountId)
    {
        accountService.triggerLock(accountId);
        return "redirect:/index";
    }

    @GetMapping("/history")
    public String accountHistory(Model model, @RequestParam(name="id") Long accountId)
    {
        Account account = accountService.getAccountById(accountId);

        model.addAttribute("user", account.getCustomer());
        model.addAttribute("account", account);
        model.addAttribute("transactions", accountService.getHistory(accountId));

        return "history";
    }
}
