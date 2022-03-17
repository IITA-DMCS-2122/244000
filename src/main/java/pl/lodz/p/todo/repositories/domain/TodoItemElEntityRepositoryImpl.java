package pl.lodz.p.todo.repositories.domain;

import org.hibernate.search.mapper.orm.Search;
import pl.lodz.p.todo.domain.TodoItemEntity;
import pl.lodz.p.todo.repositories.domain.TodoItemElEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TodoItemElEntityRepositoryImpl implements TodoItemElEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TodoItemEntity> search(String searchString) {
        return Search.session(entityManager)
                .search(TodoItemEntity.class)
                .where(result -> result.match()
                        .fields("title", "description")
                        .matching(searchString))
                .fetchAllHits();
    }
}
