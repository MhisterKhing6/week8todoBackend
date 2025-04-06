package dyanamo.todo.todo.service;

import dyanamo.todo.todo.model.Todo;
import dyanamo.todo.todo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    public Todo getTodoById(String id) {
        return todoRepository.findById(id);
    }
    
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    public Todo updateTodo(String id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id);
        if (todo != null) {
            todo.setTitle(todoDetails.getTitle());
            todo.setDescription(todoDetails.getDescription());
            todo.setStatus(todoDetails.getStatus());
            todo.setDueTime(todoDetails.getDueTime());
            return todoRepository.save(todo);
        }
        return null;
    }
    
    public void deleteTodo(String id) {
        todoRepository.delete(id);
    }
}