package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.DTO.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;

public interface RestaurantOwnerService {
    String saveRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO);

    boolean ownerExistByEmail(String email);

    String verifyRestaurantOwner(VerifyOTP verifyOTP);

    String resendOTP(String email);

    String forgetPassword(String email);

    String changePassword(ChangePasswordDTO changePasswordDTO);
}
