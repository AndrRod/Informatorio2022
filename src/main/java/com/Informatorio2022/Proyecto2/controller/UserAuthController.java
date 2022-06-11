package com.Informatorio2022.Proyecto2.controller;
import com.Informatorio2022.Proyecto2.exception.RefreshTokenForm;
import com.Informatorio2022.Proyecto2.dtos.UserLoginResponseDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserPartDto> registerUser(@RequestBody UserPartDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody LoginForm user, HttpServletRequest request){
        return ResponseEntity.ok(userService.userLogin(user.getEmail(), user.getPassword(), request));
    }
    @GetMapping("/accessdenied")
    public ResponseEntity<MessageInfo> accesDenied (HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageInfo(messageResum.message("user.not.access", null), 403, request.getRequestURI()));
    }
    @GetMapping("/logoutsuccess")
    public ResponseEntity<MessageInfo> logout (HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageInfo(messageResum.message("user.logout", null), 202, request.getRequestURI()));
    }
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void refreshToken(@RequestBody RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(form, request, response);
    }
}
@Data
class LoginForm{
    private String email;
    private String password;
}

