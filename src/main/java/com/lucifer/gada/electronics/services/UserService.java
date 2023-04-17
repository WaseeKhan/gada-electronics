package com.lucifer.gada.electronics.services;

import com.lucifer.gada.electronics.dtos.PageableResponse;
import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId) throws IOException;

    PageableResponse<UserDto> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    UserDto getSingleUser(String userId);

    //Search

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);

}
