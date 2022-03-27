package pl.lodz.p.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.lodz.p.todo.enums.EventStatus;

import javax.persistence.Id;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private String id;
    private String businessKey;
    private String eventType;
    private TodoItemEntity todoItem;
    private EventStatus status;
}
