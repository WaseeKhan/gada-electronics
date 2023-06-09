package com.lucifer.gada.electronics.services.Impl;

import com.lucifer.gada.electronics.dtos.PageableResponse;
import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import com.lucifer.gada.electronics.exceptions.ResourceNotFoundException;
import com.lucifer.gada.electronics.helper.UserIdGenerator;
import com.lucifer.gada.electronics.helper.UserPageable;
import com.lucifer.gada.electronics.repositories.UserRepository;
import com.lucifer.gada.electronics.services.UserService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.modelmapper.ModelMapper;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserIdGenerator userIdGenerator;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {


        //generate Unique ID
        String userId = UUID.randomUUID().toString();
//        String userId = userIdGenerator.generate().toString();
        userDto.setUserId(userId);
        userDto.setCreatedOn(new Date());
        //dto->entity
        User user = dtoToEntity(userDto);
        User entityUser = userRepository.save(user);
        //entity->dto
        UserDto dtoUser = entityToDto(entityUser);
        return dtoUser;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
    public void deleteUser(String userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //delete user Image When User delete


        String fullPath = imagePath + user.getImageName();//will get sample.png
        logger.info("fullPath:{}", fullPath);
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){

            logger.info("User Image not found in folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

//        List<User> users = page.getContent();
//        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//        PageableResponse<UserDto> response = new PageableResponse<>();
//        response.setContent(dtoList);
//        response.setPageNo(page.getNumber());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());

        PageableResponse<UserDto> response = UserPageable.getPageableResponse(page, UserDto.class);

        return response;
   }

    @Override
    public UserDto getSingleUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email"));
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


