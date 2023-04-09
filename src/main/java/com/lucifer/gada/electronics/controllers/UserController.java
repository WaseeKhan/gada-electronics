package com.lucifer.gada.electronics.controllers;

import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import com.lucifer.gada.electronics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDto userDto
            ){
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted Succusfully!!", HttpStatus.OK);

    }

    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @PostMapping("{/userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId){
        UserDto singleUser = userService.getSingleUser(userId);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @PostMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        UserDto userEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(userEmail, HttpStatus.OK);
    }


    @PostMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        List<UserDto> searchResult = userService.searchUser(keyword);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
