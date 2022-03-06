package pl.lodz.p.todo.controllers;

import org.springframework.web.bind.annotation.*;
import pl.lodz.p.todo.entities.TodoItem;
import pl.lodz.p.todo.services.TodoItemService;

import java.util.List;

@RestController
@RequestMapping(value = "/items")
public class TodoItemController {

    private final TodoItemService todoItemService;

    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping
    public List<TodoItem> getItems() {
        return todoItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public TodoItem getItemById(@PathVariable Long id) {
        return todoItemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable Long id) {
        todoItemService.deleteItemById(id);
    }

    @PostMapping
    public TodoItem createItem(@RequestBody TodoItem todoItem) {
        return todoItemService.addItem(todoItem);
    }

    @PutMapping
    public TodoItem updateItem(@RequestBody TodoItem todoItem) {
        return todoItemService.updateItem(todoItem);
    }
}
