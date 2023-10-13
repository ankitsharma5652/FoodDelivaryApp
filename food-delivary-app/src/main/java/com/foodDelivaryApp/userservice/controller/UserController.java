package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.UserUpdateDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long id , @RequestParam("image") MultipartFile file){
        try {
            String successMessage = userService.uploadImages(id , file);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot Upload images due to internal server error");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long id){
        try {
            User user = userService.findUserById(id);
            if (user!=null){
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the User due to internal server error");
    }

    @PostMapping("/updateProfileImage/{id}")
    public ResponseEntity<?> updateProfilePicture(@PathVariable("id") Long id ,  @RequestParam("image") MultipartFile file){
        try {
            String imageUpdateMessage = userService.updateImage(id, file);
            if (imageUpdateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(imageUpdateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cannot update images due to internal server error");
    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUserDetails(@Valid @PathVariable("id") Long id , @RequestBody UserUpdateDTO userUpdateDTO){
        try {
            User updateUser = userService.updateUser(id , userUpdateDTO);
            if (updateUser!=null){
                return ResponseEntity.status(HttpStatus.OK).body("User Updated Successfully !");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update user details due to internal server error");
    }



}
