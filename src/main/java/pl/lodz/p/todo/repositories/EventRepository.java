package pl.lodz.p.todo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.Event;

import java.util.Optional;

@Transactional(transactionManager = "mongoTransactionManager")
public interface EventRepository extends MongoRepository<Event, String> {

    void deleteByBusinessKey(String businessKey);
    Optional<Event> findByBusinessKey(String businessKey);
}
