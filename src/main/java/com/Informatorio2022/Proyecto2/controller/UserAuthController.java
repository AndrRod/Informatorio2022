package com.Informatorio2022.Proyecto2.controller;
import com.Informatorio2022.Proyecto2.dtos.UserLoginResponseDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
 import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    public void refreshToken(@RequestBody refreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(form != null && form.getRefresh_token().startsWith("Bearer ")){
            try {
                String refresh_token = form.getRefresh_token().substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = userService.findUserByEmail(email);
                String acceso_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutos
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", Optional.ofNullable(user.getRole().getAuthority()).stream().collect(Collectors.toList()))
                        .sign(algorithm);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),  new HashMap<>(){{put("message", "the user " + user.getEmail()+ " refresh the token succesfully"); put("access_token", acceso_token); put("update_token", refresh_token);}});
            }catch (Exception exception){
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), new MessageInfo(exception.getMessage(), 403, request.getRequestURI()));
            }
        }else {
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), new MessageInfo(messageResum.message("token.refresh.error", null), 403, request.getRequestURI()));
        }
    }
}
@Data
class LoginForm{
    private String email;
    private String password;
}
@Data
class refreshTokenForm{
    private String refresh_token;
}
