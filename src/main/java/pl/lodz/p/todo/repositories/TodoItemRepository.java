package pl.lodz.p.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.todo.entities.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
