package com.lucifer.gada.electronics.services.Impl;

import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import com.lucifer.gada.electronics.repositories.UserRepository;
import com.lucifer.gada.electronics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        
        //generate Unique ID
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto->entity
        User user = dtoToEntity(userDto);
        User entityUser = userRepository.save(user);

        //entity->dto
        UserDto dtoUser = entityToDto(entityUser);

        return dtoUser;
    }

    private UserDto entityToDto(User entityUser) {
        UserDto userDto = UserDto.builder()
                .userId(entityUser.getUserId())
                .name(entityUser.getName())
                .email(entityUser.getEmail())
                .password(entityUser.getPassword())
                .about(entityUser.getAbout())
                .gender(entityUser.getGender())
                .imageName(entityUser.getImageName()).build();
        return userDto;

    }

    private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .userId(userDto.getName())
                .userId(userDto.getAbout())
                .userId(userDto.getGender())
                .userId(userDto.getEmail())
                .userId(userDto.getPassword())
                .userId(userDto.getImageName())
                .build();

        return user;
    }

    @Override
    public UserDto updateUser(String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public List<UserDto> getSingleUser(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        return null;
    }
}
