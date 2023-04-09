package com.lucifer.gada.electronics.services.Impl;

import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import com.lucifer.gada.electronics.repositories.UserRepository;
import com.lucifer.gada.electronics.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);

        return updatedDto;
    }
    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getSingleUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with given email"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDto entityToDto(User entityUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(entityUser.getUserId())
//                .name(entityUser.getName())
//                .email(entityUser.getEmail())
//                .password(entityUser.getPassword())
//                .about(entityUser.getAbout())
//                .gender(entityUser.getGender())
//                .imageName(entityUser.getImageName()).build();
        return modelMapper.map(entityUser, UserDto.class);

    }
    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .userId(userDto.getName())
//                .userId(userDto.getAbout())
//                .userId(userDto.getGender())
//                .userId(userDto.getEmail())
//                .userId(userDto.getPassword())
//                .userId(userDto.getImageName())
//                .build();

        return modelMapper.map(userDto, User.class);
    }




}


