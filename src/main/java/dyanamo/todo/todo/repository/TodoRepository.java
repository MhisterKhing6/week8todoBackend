package dyanamo.todo.todo.repository;

import dyanamo.todo.todo.model.Todo;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "TodoItems";
    
    public TodoRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }
    
    public List<Todo> findAll() {
        ScanRequest scanRequest = ScanRequest.builder()
            .tableName(tableName)
            .build();
            
        ScanResponse response = dynamoDbClient.scan(scanRequest);
        return response.items().stream()
            .map(this::mapTodoFromDynamoDB)
            .collect(Collectors.toList());
    }
    
    public Todo findById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());
        
        GetItemRequest request = GetItemRequest.builder()
            .tableName(tableName)
            .key(key)
            .build();
            
        GetItemResponse response = dynamoDbClient.getItem(request);
        return mapTodoFromDynamoDB(response.item());
    }
    
  
    public Todo save(Todo todo) {
        Map<String, AttributeValue> item = new HashMap<>();
            
        item.put("id", AttributeValue.builder().s(todo.getId()).build());
        item.put("title", AttributeValue.builder().s(todo.getTitle()).build());
        item.put("description", AttributeValue.builder().s(todo.getDescription()).build());
        item.put("status", AttributeValue.builder().s(todo.getStatus()).build());
        item.put("dueTime", AttributeValue.builder().s(String.valueOf(todo.getDueTime())).build());
    
        PutItemRequest putItemRequest = PutItemRequest.builder()
            .tableName(tableName)
            .item(item)
            .build();
    
        dynamoDbClient.putItem(putItemRequest);
            
            return todo; 
    }
    

    private Todo mapTodoFromDynamoDB(Map<String, AttributeValue> item) {
        Todo todo = new Todo();
        
        if (item.containsKey("id")) {
            todo.setId(item.get("id").s());
        }
        
        if (item.containsKey("title")) {
            todo.setTitle(item.get("title").s());
        }
        
        if (item.containsKey("description")) {
            todo.setDescription(item.get("description").s());
        }
        
        if (item.containsKey("status")) {
            todo.setStatus(item.get("status").s());
        }
        
        if (item.containsKey("dueTime")) {
            todo.setDueTime(LocalDateTime.parse(item.get("dueTime").s()));
        }
        

        return todo;
    }
    
    
        public void delete(String id) {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("id", AttributeValue.builder().s(id).build());
        
            DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();
        
            dynamoDbClient.deleteItem(deleteItemRequest);
        }
    
   
}