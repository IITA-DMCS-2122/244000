package pl.lodz.p.todo.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.todo.domain.Event;
import pl.lodz.p.todo.domain.TodoItemEntity;
import pl.lodz.p.todo.enums.EventStatus;
import pl.lodz.p.todo.repositories.EventRepository;
import pl.lodz.p.todo.repositories.analytics.TodoItemAnalyticsEntityRepository;
import pl.lodz.p.todo.repositories.domain.TodoItemEntityRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private static final String COLLECTION_NAME = "event";
    private static final String FIELD_NAME = "status";

    private final TodoItemAnalyticsEntityRepository todoItemAnalyticsEntityRepository;
    private final TodoItemEntityRepository todoItemEntityRepository;
    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;

    @Scheduled(cron = "0 * * * * *")
    @Transactional(transactionManager = "chainedTransactionManager")
    public void addItems() {
        log.info("ADDING ITEMS TO POSTGRESQL STARTED");
        List<Event> newEvents = eventRepository.findAll().stream().filter(event -> event.getStatus().equals(EventStatus.NEW)).toList();
        List<TodoItemEntity> todoItems = newEvents.stream().map(Event::getTodoItem).toList();
        todoItems.forEach(item -> item.setBusinessKey(UUID.randomUUID().toString()));
        todoItemAnalyticsEntityRepository.saveAll(todoItems);
        todoItemEntityRepository.saveAll(todoItems);
        newEvents.forEach(event -> event.setStatus(EventStatus.CREATED));
        eventRepository.saveAll(newEvents);
    }

    public int count() {
        TypedAggregation<EventAmount> aggregation = Aggregation.newAggregation(
                EventAmount.class,
                Aggregation.match(Criteria.where(FIELD_NAME).is("CREATED")),
                Aggregation.unwind(FIELD_NAME),
                Aggregation.group().count().as("count")
        );
        AggregationResults<EventAmount> result = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, EventAmount.class);
        return result.getMappedResults().get(0).getCount();
    }

    @Data
    private static class EventAmount {
        private int count;
    }
}
