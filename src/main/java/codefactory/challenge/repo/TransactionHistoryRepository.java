package codefactory.challenge.repo;

import codefactory.challenge.model.TransactionHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {

    List<TransactionHistory> findByAccountId(@Param("account_id") Long accountId);
}
