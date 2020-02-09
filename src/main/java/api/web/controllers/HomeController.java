package api.web.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.web.models.Greetings;
import api.web.models.Person;
import api.web.server.IPersonRepository;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private IPersonRepository repo;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/home")
    public Greetings greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greetings(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/personlist")
    public List<Person> GetPerson() {
        return repo.findAll();
    }

    @PostMapping("/addperson")
    public String AddPerson(@RequestBody Person data) {

        repo.save(data);
        return "ok";
    }
}