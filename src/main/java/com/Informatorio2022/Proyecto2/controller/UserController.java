package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserCompleteDto> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.findUserById(Long.valueOf(id)));
    }
    @PostMapping
    public ResponseEntity<UserPartDto> createUser(@Valid @RequestBody UserPartDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }
    @GetMapping
    public ResponseEntity<MessagePag> getUserById(@RequestParam(value = "page", required = true) String page, WebRequest request){
        return ResponseEntity.ok(userService.findPageBy10Users(Integer.valueOf(page), request));
    }
}
