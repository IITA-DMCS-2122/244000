package pl.lodz.p.todo.dto.mappers;

import pl.lodz.p.todo.domain.TodoItemEntity;
import pl.lodz.p.todo.dto.TodoItemDto;

public class TodoItemEntityMapper {

    public static TodoItemEntity mapFromDto(TodoItemDto todoItemDto) {
        return TodoItemEntity.builder()
                .title(todoItemDto.getTitle())
                .description(todoItemDto.getDescription())
                .build();
    }

    public static TodoItemDto mapToDto(TodoItemEntity todoItemEntity) {
        return TodoItemDto.builder()
                .businessKey(todoItemEntity.getBusinessKey())
                .title(todoItemEntity.getTitle())
                .description(todoItemEntity.getDescription())
                .build();
    }
}
