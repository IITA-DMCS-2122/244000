package pl.lodz.p.todo.repositories.analytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.TodoItemEntity;

@Transactional(transactionManager = "analyticsTransactionManager")
public interface TodoItemAnalyticsEntityRepository extends JpaRepository<TodoItemEntity, Long> {
}
