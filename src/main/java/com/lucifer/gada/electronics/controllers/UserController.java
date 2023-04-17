package com.lucifer.gada.electronics.controllers;

import com.lucifer.gada.electronics.dtos.ImageApiResponse;
import com.lucifer.gada.electronics.dtos.PageableResponse;
import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import com.lucifer.gada.electronics.payload.ApiResponseMessage;
import com.lucifer.gada.electronics.services.FileService;
import com.lucifer.gada.electronics.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath="";


    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @Valid @RequestBody UserDto userDto
            ){
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) throws IOException {

        userService.deleteUser(userId);



        ApiResponseMessage message = ApiResponseMessage.builder().message("User deleted Successfully!!").success(true).status(HttpStatus.OK).build();



        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){

        PageableResponse<UserDto> allUser = userService.getAllUser(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId){
        UserDto singleUser = userService.getSingleUser(userId);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        UserDto userEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(userEmail, HttpStatus.OK);
    }


    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        List<UserDto> searchResult = userService.searchUser(keyword);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }


    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageApiResponse> uploadUserImage(
            @RequestParam("userImage")MultipartFile image, @PathVariable String userId
            ) throws IOException {
        String imageName = fileService.uploadImage(image, imageUploadPath);
        UserDto user = userService.getSingleUser(userId);
        user.setImageName(imageName);
        userService.updateUser(user, userId);
        ImageApiResponse imageResponse = ImageApiResponse.builder().ImageName(imageName).success(true).path(imageUploadPath).ImageName(user.getImageName()).message("User Image uploaded successfully !!").status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getSingleUser(userId );
        logger.info("User Image Name {}", user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
