package dyanamo.todo.todo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellthController {
    

    @GetMapping("/")
    ResponseEntity<String> health() {
        return ResponseEntity.ok("Done");
    }
}
