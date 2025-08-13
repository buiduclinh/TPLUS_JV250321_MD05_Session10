package com.example.ex.service;

import com.example.ex.model.Room;
import com.example.ex.repo.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImp implements RoomService {
    @Autowired
    RoomDAO roomDAO;

    @Override
    public List<Room> getRooms() {
        return roomDAO.getRooms();
    }

    @Override
    public Room getRoom(int id) {
        return roomDAO.getRoom(id);
    }

    @Override
    public boolean addRoom(Room room) {
        return roomDAO.addRoom(room);
    }

    @Override
    public boolean deleteRoom(int id) {
        return roomDAO.deleteRoom(id);
    }

    @Override
    public boolean updateRoom(Room room) {
        return roomDAO.updateRoom(room);
    }
}
