package dyanamo.todo.todo.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {
    private String id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime dueTime;

    
    // Default constructor for DynamoDB
    public Todo() {
        this.id = UUID.randomUUID().toString();
        this.dueTime = LocalDateTime.now();
        this.status = "PENDING";
    }
    
}