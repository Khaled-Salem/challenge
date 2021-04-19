package codefactory.challenge.service;

import codefactory.challenge.model.Account;
import codefactory.challenge.model.Customer;
import codefactory.challenge.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepo;

    public Customer getCustomerById(Long id) throws Exception
    {
        Optional<Customer> customer = customerRepo.findById(id);

        if(customer.isPresent())
            return customer.get();
        else
            throw new Exception("Customer not found with id " + id);
    }
}
