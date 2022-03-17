package pl.lodz.p.todo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.TodoItemDocument;

import java.util.Optional;

@Transactional(transactionManager = "mongoTransactionManager")
public interface TodoItemDocumentRepository extends MongoRepository<TodoItemDocument, String> {

    void deleteByBusinessKey(String businessKey);
    Optional<TodoItemDocument> findByBusinessKey(String businessKey);
}
