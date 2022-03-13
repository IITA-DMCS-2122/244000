package pl.lodz.p.todo.dto.mappers;

import pl.lodz.p.todo.domain.TodoItemDocument;
import pl.lodz.p.todo.dto.TodoItemDto;

public class TodoItemDocumentMapper {

    public static TodoItemDocument mapFromDto(TodoItemDto todoItemDto) {
        return TodoItemDocument.builder()
                .title(todoItemDto.getTitle())
                .description(todoItemDto.getDescription())
                .build();
    }

    public static TodoItemDto mapToDto(TodoItemDocument todoItemDocument) {
        return TodoItemDto.builder()
                .businessKey(todoItemDocument.getBusinessKey())
                .title(todoItemDocument.getTitle())
                .description(todoItemDocument.getDescription())
                .build();
    }
}
