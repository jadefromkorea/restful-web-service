package com.example.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if(user == null) {
            throw  new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

//    @GetMapping("/users/{id}")
//    public EntityModel<User> retrieveUser(@PathVariable int id) {
//        User user = service.findOne(id);
//
//        if(user == null) {
//            throw  new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//
//        // HATEOAS
//        EntityModel<User> model = new EntityModel<>(user);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        model.add(linkTo.withRel("all-users"));
//
//        return model;
//    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/users1")
    public User createUser1(@RequestBody User user) {
        return service.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deletUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

    }
}
