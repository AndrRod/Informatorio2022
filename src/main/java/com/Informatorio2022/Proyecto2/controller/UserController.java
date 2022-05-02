package com.Informatorio2022.Proyecto2.controller;
import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserLoginResponseDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserCompleteDto> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.findUserById(Long.valueOf(id)));
    }

    @GetMapping
    public ResponseEntity<MessagePag> getUserById(@RequestParam(value = "page", required = true) String page, HttpServletRequest request){
        return ResponseEntity.ok(userService.findPageBy10Users(Integer.valueOf(page), request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserPartDto> userUpdate(@PathVariable String id, @RequestBody UserPartDto userPartDto){
        return ResponseEntity.ok(userService.updateUser(Long.valueOf(id), userPartDto));
    }
    @PutMapping("/role/{id}")
    public ResponseEntity<MessageInfo> userUpdateRole(@PathVariable String id, @RequestBody AddRoleToUserForm role, HttpServletRequest request){
        userService.updateUserRol(Long.valueOf(id), role.getRoleName());
        return ResponseEntity.ok(new MessageInfo(messageResum.message("user.has.update.role", role.getRoleName()), 200, request.getRequestURI()));
    }

}
@Data
class AddRoleToUserForm{
    private String roleName;
}

