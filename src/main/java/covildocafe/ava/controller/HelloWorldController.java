package covildocafe.ava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/health")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Service is up and running.");
    }
}
