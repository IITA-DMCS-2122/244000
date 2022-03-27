package pl.lodz.p.todo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.Event;
import pl.lodz.p.todo.domain.TodoItemEntity;
import pl.lodz.p.todo.dto.TodoItemDto;
import pl.lodz.p.todo.dto.mappers.TodoItemEntityMapper;
import pl.lodz.p.todo.enums.EventStatus;
import pl.lodz.p.todo.repositories.EventRepository;
import pl.lodz.p.todo.repositories.domain.TodoItemEntityRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoItemService {

    private final TodoItemEntityRepository todoItemEntityRepository;
    private final EventRepository eventRepository;

    public void addItem(TodoItemDto todoItemDto) {
        String businessKey = UUID.randomUUID().toString();
        Event event = new Event();
        event.setBusinessKey(businessKey);
        event.setEventType("CREATE");
        event.setTodoItem(TodoItemEntityMapper.mapFromDto(todoItemDto));
        event.setStatus(EventStatus.NEW);
        eventRepository.save(event);
    }

    public TodoItemDto getItemById(Long id) {
        return todoItemEntityRepository.findById(id)
                .map(TodoItemEntityMapper::mapToDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<TodoItemDto> getAllItems() {
        return todoItemEntityRepository.findAll().stream().map(TodoItemEntityMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(transactionManager = "chainedTransactionManager")
    public void deleteItemById(String businessKey) {
        todoItemEntityRepository.deleteByBusinessKey(businessKey);
        eventRepository.deleteByBusinessKey(businessKey);
    }

    @Transactional(transactionManager = "chainedTransactionManager")
    public void updateItem(TodoItemDto todoItemDto) {
        TodoItemEntity todoItemEntity = todoItemEntityRepository.findByBusinessKey(todoItemDto.getBusinessKey())
                .orElseThrow(IllegalArgumentException::new);

        todoItemEntity.setTitle(todoItemDto.getTitle());
        todoItemEntity.setDescription(todoItemDto.getDescription());
        todoItemEntityRepository.saveAndFlush(todoItemEntity);
    }

    public List<TodoItemDto> search(String searchString) {
        return todoItemEntityRepository.search(searchString).stream().map(TodoItemEntityMapper::mapToDto).collect(Collectors.toList());
    }
}
