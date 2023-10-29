package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 40, message = "Invalid Username [username should be b/w 3-40 characters]")
    private String username;

    @Email
    @NotNull
    @Size(min = 8, max = 60, message = "Invalid Email [email should be b/w 8-60 characters]")
    @Column(unique = true , nullable = false)
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30, message = "Invalid firstName [firstName should be 5-30 characters]")
    private String firstName;

    @Size(max = 30, message = "Invalid lastName [lastName should be maximum 30 characters]")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private String countryCode;

    @Size(max = 15, message = "Invalid phoneNumber [phoneNumber should be maximum 15 characters]")
    private String phoneNumber;

    @Size(min = 5 ,max = 100, message = "Invalid address [address should be maximum 100 characters]")
    private String address;

    @Size(min = 3 , max = 50, message = "Invalid city [city should be maximum 50 characters]")
    private String city;

    @Size(min = 2 , max = 50, message = "Invalid state [state should be maximum 50 characters]")
    private String state;

    @Size(min = 2 ,max = 5, message = "Invalid country [country should be maximum 5 characters]")
    private String country;

    @Size(min = 5 ,max = 10, message = "Invalid postalCode [postalCode should be b/w 5-10 characters]")
    private String postalCode;
    @JsonIgnore
    private String fileName;
    @JsonIgnore
    private String fileType;
    @JsonIgnore
    @Lob
    @Column(name="imageData", length = 1000)
    private byte[] profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isVerified;
    private boolean isActive;
    private String preferredLanguage;
    private Integer otp;
    private LocalDateTime otpSendingTime;
    private LocalDateTime otpExpireTime;


    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles" ,
            joinColumns =@JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Roles> roles;

    @OneToOne
    private Cart cart;



}
