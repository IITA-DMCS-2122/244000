package pl.lodz.p.todo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.TodoItemDocument;
import pl.lodz.p.todo.domain.TodoItemEntity;
import pl.lodz.p.todo.dto.TodoItemDto;
import pl.lodz.p.todo.dto.mappers.TodoItemDocumentMapper;
import pl.lodz.p.todo.dto.mappers.TodoItemEntityMapper;
import pl.lodz.p.todo.repositories.TodoItemDocumentRepository;
import pl.lodz.p.todo.repositories.TodoItemEntityRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoItemService {

    private final TodoItemEntityRepository todoItemEntityRepository;
    private final TodoItemDocumentRepository todoItemDocumentRepository;

    public TodoItemService(TodoItemEntityRepository todoItemEntityRepository, TodoItemDocumentRepository todoItemDocumentRepository) {
        this.todoItemEntityRepository = todoItemEntityRepository;
        this.todoItemDocumentRepository = todoItemDocumentRepository;
    }

    public void addItem(TodoItemDto todoItemDto) {
        String businessKey = UUID.randomUUID().toString();

        TodoItemEntity todoItemEntity = TodoItemEntityMapper.mapFromDto(todoItemDto);
        todoItemEntity.setBusinessKey(businessKey);
        todoItemEntityRepository.saveAndFlush(todoItemEntity);

        TodoItemDocument todoItemDocument = TodoItemDocumentMapper.mapFromDto(todoItemDto);
        todoItemDocument.setBusinessKey(businessKey);
        todoItemDocumentRepository.save(todoItemDocument);
    }

    public TodoItemDto getItemById(Long id) {
        return todoItemEntityRepository.findById(id)
                .map(TodoItemEntityMapper::mapToDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<TodoItemDto> getAllItems() {
        return todoItemEntityRepository.findAll().stream().map(TodoItemEntityMapper::mapToDto).collect(Collectors.toList());
    }

    public void deleteItemById(String businessKey) {
        todoItemEntityRepository.deleteByBusinessKey(businessKey);
        todoItemDocumentRepository.deleteByBusinessKey(businessKey);
    }

    public void updateItem(TodoItemDto todoItemDto) {
        TodoItemEntity todoItemEntity = todoItemEntityRepository.findByBusinessKey(todoItemDto.getBusinessKey())
                .orElseThrow(IllegalArgumentException::new);
        TodoItemDocument todoItemDocument = todoItemDocumentRepository.findByBusinessKey(todoItemDto.getBusinessKey())
                .orElseThrow(IllegalArgumentException::new);

        todoItemEntity.setTitle(todoItemDto.getTitle());
        todoItemEntity.setDescription(todoItemDto.getDescription());
        todoItemEntityRepository.saveAndFlush(todoItemEntity);

        todoItemDocument.setTitle(todoItemDto.getTitle());
        todoItemDocument.setDescription(todoItemDto.getDescription());
        todoItemDocumentRepository.save(todoItemDocument);
    }

    public List<TodoItemDto> search(String searchString) {
        return todoItemEntityRepository.search(searchString).stream().map(TodoItemEntityMapper::mapToDto).collect(Collectors.toList());
    }
}
