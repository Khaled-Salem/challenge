package codefactory.challenge.controller;

import codefactory.challenge.model.Account;
import codefactory.challenge.model.Customer;
import codefactory.challenge.service.AccountService;
import codefactory.challenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreatePageController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @GetMapping("/create_account/{id}")
    public String index(Model model, @PathVariable String id) throws Exception {
        Account propAccount = new Account();
        Customer user = customerService.getCustomerById(Long.parseLong(id));
        propAccount.setCustomer(user);
        model.addAttribute("user", user);
        model.addAttribute("account", propAccount);
        model.addAttribute("types", accountService.getPossibleAccounts(user));

        return "create_account";
    }

    @PostMapping("/create")
    public String createAccount(Account account, Model model)
    {
        accountService.populateAccount(account);
        model.addAttribute("user", account.getCustomer());
        model.addAttribute("accounts", accountService.getAllAccountsByCustomerId(account.getCustomer().getId()));
        return "redirect:/index";
    }
}
