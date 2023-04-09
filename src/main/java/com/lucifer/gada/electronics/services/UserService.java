package com.lucifer.gada.electronics.services;

import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto> getAllUser();

    UserDto getSingleUser(String userId);

    //Search

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);

}
