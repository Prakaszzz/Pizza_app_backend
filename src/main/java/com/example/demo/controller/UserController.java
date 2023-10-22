package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/res/v1/")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/validate/user/{name}")
    public ResponseEntity<?> validateUser(@PathVariable String name){
        return userService.validateUser(name);
    }

    @PostMapping("/new/user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<?> getUser(@PathVariable String name){
       return userService.getUser(name);
    }

    @PostMapping("/address")
    public ResponseEntity<?> addUserAddress(@RequestBody Address address){
      return userService.addAddress(address);
    }

    @GetMapping("/user/{userId}/address")
    public ResponseEntity<?> getAllAddress(@PathVariable String userId){
        return userService.getAddress(userId);
    }
}
