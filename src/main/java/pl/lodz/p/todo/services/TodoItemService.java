package pl.lodz.p.todo.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.todo.entities.TodoItem;
import pl.lodz.p.todo.repositories.TodoItemRepository;

import java.util.List;

@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;

    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public TodoItem addItem(TodoItem todoItem) {
        return todoItemRepository.saveAndFlush(todoItem);
    }

    public TodoItem getItemById(Long id) {
        return todoItemRepository.findById(id).get();
    }

    public List<TodoItem> getAllItems() {
        return todoItemRepository.findAll();
    }

    public void deleteItemById(Long id) {
        todoItemRepository.deleteById(id);
    }

    public TodoItem updateItem(TodoItem todoItem) {
        TodoItem item = todoItemRepository.findById(todoItem.getId()).get();
        item.setTitle(todoItem.getTitle());
        item.setDescription(todoItem.getDescription());
        return todoItemRepository.saveAndFlush(item);
    }
}
