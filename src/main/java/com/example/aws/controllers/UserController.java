package com.example.aws.controllers;

import com.example.aws.entities.User;
import com.example.aws.exceptions.ResourceNotFoundException;
import com.example.aws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId) {
        return this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userRepo.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepo.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId){
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
        this.userRepo.delete(existingUser);
        return ResponseEntity.ok().build();
    }

}
