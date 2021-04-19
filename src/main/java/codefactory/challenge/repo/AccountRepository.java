package codefactory.challenge.repo;

import codefactory.challenge.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findByCustomerId(@Param("customer_id") Long customerId);

    List<Account> findByTypeAndCustomerId(@Param("type") String type, @Param("customer_id") Long customerId);
}
