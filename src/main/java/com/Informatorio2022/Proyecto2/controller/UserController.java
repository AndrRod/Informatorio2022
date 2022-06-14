package com.Informatorio2022.Proyecto2.controller;
import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserDtoUpdate;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@CrossOrigin(origins = {"http://127.0.0.1:3000/", "http://127.0.0.1:5500/"})
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
    public ResponseEntity<UserPartDto> userUpdate(@PathVariable String id, @RequestBody UserDtoUpdate userDtoUpdate){
        return ResponseEntity.ok(userService.updateUser(Long.valueOf(id), userDtoUpdate));
    }
    @PutMapping("/role/{id}")
    public ResponseEntity<MessageInfo> userUpdateRole(@PathVariable String id, @RequestBody AddRoleToUserForm role, HttpServletRequest request){
        userService.updateUserRol(Long.valueOf(id), role.getRoleName());
        return ResponseEntity.ok(new MessageInfo(messageResum.message("user.has.update.role", role.getRoleName()), 200, request.getRequestURI()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageInfo> deleteUserById(@PathVariable String id, HttpServletRequest request){
        userService.deleteUserById(Long.valueOf(id));
        return ResponseEntity.ok(new MessageInfo(messageResum.message("user.delete.ok", id), 200, request.getRequestURI()));
    }
//    Practica de QUERYS
    @PostMapping("/byname/{page}")
    public ResponseEntity<?> listFindByFirstName(@RequestBody ListByName listByName, @PathVariable Integer page){
        return ResponseEntity.ok(userService.findListByFirstName(listByName.getName(), page));
    }
    @PostMapping("/bydates/{page}")
    public ResponseEntity<?> listBayBetweenDates(@RequestBody FormDates formDates, @PathVariable Integer page){
        return ResponseEntity.ok(userService.findByCreationDate(formDates.getStartDate(), formDates.getFinishDate(), page));
    }
}
@Data
class AddRoleToUserForm{
    private String roleName;
}
@Data
class ListByName{
    private String name;
}
@Data
class FormDates{
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate finishDate;
}
