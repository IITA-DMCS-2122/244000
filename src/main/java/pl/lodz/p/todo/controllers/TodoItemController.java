package pl.lodz.p.todo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.todo.dto.TodoItemDto;
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
    public List<TodoItemDto> getItems() {
        return todoItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(todoItemService.getItemById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{businessKey}")
    public void deleteItemByKey(@PathVariable String businessKey) {
        todoItemService.deleteItemById(businessKey);
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody TodoItemDto todoItemEntity) {
        todoItemService.addItem(todoItemEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody TodoItemDto todoItemEntity) {
        try {
            todoItemService.updateItem(todoItemEntity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
