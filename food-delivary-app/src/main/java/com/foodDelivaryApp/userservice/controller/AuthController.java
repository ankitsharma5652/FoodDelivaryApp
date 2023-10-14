package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.*;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.RestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    RestaurantOwnerService restaurantOwnerService;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        try {
            if (userService.userAlreadyExistByEmailOrUserName(userDTO.getEmail() , userDTO.getUsername())){
                return ResponseEntity.status(HttpStatus.OK).body("User is already register with the same email or username");
            }

            User user = UserConvertor.convertUserDtoToUserEntity(userDTO);
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String registrationMessage =  userService.saveUser(user);
            if (registrationMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Register the User due to Invalid request");
    }



    @PostMapping("/verifyOtp")
    public  ResponseEntity<?> verifyOtp(@RequestBody VerifyOTP verifyOTP){
        try {
            String verifyUser = userService.verifyUserAccount(verifyOTP);
            if (verifyUser!=null){
                return ResponseEntity.status(HttpStatus.OK).body(verifyUser);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot verify OTP with id  due to internal server error");
    }

    @GetMapping("/resendOtp")
    public ResponseEntity<?> resendOTP(@RequestParam("email") String email){
        try {
            String successMessage = userService.resendOTP(email);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);          }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot resend OTP with id due to internal server error");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        try {
            String forgetPasswordMessage = userService.forgetPassword(forgetPasswordDTO.getEmail());
            if (forgetPasswordMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(forgetPasswordDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot reset the password");
    }

    @PostMapping("/changesPassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
    try {
        String changePasswordMessage = userService.changePassword(changePasswordDTO);
        if (changePasswordMessage!=null){
            return ResponseEntity.status(HttpStatus.OK).body(changePasswordMessage);
        }
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot change password due to interval server error");
    }



    @PostMapping("/restaurant-owner")
    public ResponseEntity<?> registerRestaurantOwner(@Valid  @RequestBody RestaurantOwnerDTO restaurantOwnerDTO){
       try {
           if (restaurantOwnerService.ownerExistByEmail(restaurantOwnerDTO.getEmail())){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already register with the same email , plz try again with a new email");
           }
           String signupMessage = restaurantOwnerService.saveRestaurantOwner(restaurantOwnerDTO);
           if (signupMessage!=null){
               return ResponseEntity.status(HttpStatus.CREATED).body(signupMessage);
           }
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot register Restaurant Owner due to invalid request");
    }

    @PostMapping("/verifyRestaurantOwner")
    public ResponseEntity<?> verifyRestaurantOwner(@RequestBody VerifyOTP verifyOTP){
        try {
            String userVerfiyMessage = restaurantOwnerService.verifyRestaurantOwner(verifyOTP);
            if (userVerfiyMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(userVerfiyMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot verify the user due to invalid request !");
    }

    @PostMapping("/requestNewOTP")
    public ResponseEntity<?> resendOTPToRestaurantOwner(@RequestParam("email") String email){
        try {
            String resendOTPMessage = restaurantOwnerService.resendOTP(email);
            if (resendOTPMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(resendOTPMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot send the otp due to invalid request");
    }


    @PostMapping("restaurantOwner/forget-password")
    public ResponseEntity<?> forgetPasswordForRestaurantOwner(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        try {
            String forgotPasswordMessage = restaurantOwnerService.forgetPassword(forgetPasswordDTO.getEmail());
            if (forgotPasswordMessage!=null) {
                return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Request");
    }


    @PostMapping("restaurant/changesPassword")
    public ResponseEntity<?> changePasswordForRestaurantOwner(@RequestBody ChangePasswordDTO changePasswordDTO){
        try {
            String changePasswordMessage = restaurantOwnerService.changePassword(changePasswordDTO);
            if (changePasswordMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(changePasswordMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot change password due to interval server error");
    }



}
