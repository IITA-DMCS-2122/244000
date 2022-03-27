package pl.lodz.p.todo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.todo.dto.TodoItemDto;
import pl.lodz.p.todo.services.EventService;
import pl.lodz.p.todo.services.TodoItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/items")
public class TodoItemController {

    private final TodoItemService todoItemService;
    private final EventService eventService;

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
    public ResponseEntity<?> createItem(@RequestBody TodoItemDto todoItemDto) {
        todoItemService.addItem(todoItemDto);
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

    @GetMapping("/search/{searchString}")
    public List<TodoItemDto> search(@PathVariable String searchString) {
        return todoItemService.search(searchString);
    }

    @GetMapping("/count")
    public int countTodoItems() {
        return eventService.count();
    }
}
