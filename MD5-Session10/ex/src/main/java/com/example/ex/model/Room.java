package com.example.ex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    private int id;
    private String roomName;
    private String roomType;
    private Role status;

    public enum Role {
        PLACED, EMPTY
    }

    private boolean isDelete;
    private double price;
    private String image;
}
