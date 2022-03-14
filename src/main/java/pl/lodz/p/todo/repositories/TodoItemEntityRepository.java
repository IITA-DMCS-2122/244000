package pl.lodz.p.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.TodoItemEntity;

import java.util.Optional;

@Transactional
public interface TodoItemEntityRepository extends JpaRepository<TodoItemEntity, Long>, TodoItemElEntityRepository {

    Optional<TodoItemEntity> findByBusinessKey(String businessKey);
    void deleteByBusinessKey(String businessKey);
}
