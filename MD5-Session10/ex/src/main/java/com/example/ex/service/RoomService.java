package com.example.ex.service;

import com.example.ex.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms();

    Room getRoom(int id);

    boolean addRoom(Room room);

    boolean deleteRoom(int id);

    boolean updateRoom(Room room);

    List<Room> getRoomsCustomerView();

    boolean bookRoom(int id);
}
