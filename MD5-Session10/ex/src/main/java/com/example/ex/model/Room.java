package com.example.ex.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    private int id;
    @NotBlank (message = "Can not BLANK this field")
    private String roomName;
    private String roomType;
    private Role status;
    public enum Role {
        PLACED, EMPTY
    }
    private boolean isDelete;
    @Min(value = 1000, message = "The price must be > 1000")
    private double price;
    private String image;

    private MultipartFile imageFile;

}
