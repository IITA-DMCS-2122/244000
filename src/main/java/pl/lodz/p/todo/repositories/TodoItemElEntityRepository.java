package pl.lodz.p.todo.repositories;

import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.TodoItemEntity;

import java.util.List;

@Transactional
public interface TodoItemElEntityRepository {

    List<TodoItemEntity> search(String searchString);
}
