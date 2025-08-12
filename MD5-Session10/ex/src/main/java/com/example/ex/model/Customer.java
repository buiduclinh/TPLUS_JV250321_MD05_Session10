package com.example.ex.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private int id;
    @NotBlank(message = "Can not black this field!")
    private String fullName;
    @NotBlank(message = "Can not black this field!")
    @Size(max = 11, message = "Can not allow more than 11 words")
    private String phoneNumber;
    @NotBlank(message = "Can not black this field!")
    @Email(message = "Email is not available!")
    private String email;
    @NotBlank(message = "Can not black this field!")
    @Size(min = 6, message = "Can not allow lease than 11 words")
    private String password;
    @NotBlank(message = "Can not black this field!")
    private String address;
    Role role;

    public enum Role {
        ADMIN, CUSTOMER;
    }
}
