package com.example.ex.controller;

import com.example.ex.model.Room;
import com.example.ex.service.CloudinaryService;
import com.example.ex.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@RequestMapping(value = {"/RoomController"})
public class RoomController {
    @Autowired
    RoomService roomService;

    private final CloudinaryService cloudinaryService;

    @Autowired
    public RoomController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/viewRooms")
    public String viewRooms(Model model) {
        List<Room> rooms = roomService.getRooms();
        model.addAttribute("rooms", rooms);
        return "viewRooms";
    }

    @GetMapping("/addNewRoom")
    public String addNewRoom(Model model) {
        model.addAttribute("room", new Room());
        return "saveRoom";
    }

    @PostMapping("/saveRoom")
    public String saveRoom(@Valid @ModelAttribute("room") Room room,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "saveRoom";
        }
        roomService.addRoom(room);
        if (!room.getImageFile().isEmpty() && room.getImageFile() != null) {
            String imageUrl = cloudinaryService.uploadImage(room.getImageFile());
            room.setImage(imageUrl);
        }
        return "redirect:/RoomController/viewRooms";
    }

    @GetMapping("/editRoom/{id}")
    public String editRoom(@PathVariable("id") int id, Model model) {
        Room room = roomService.getRoom(id);
        model.addAttribute("room", room);
        return "editRoom";
    }

    @PostMapping("/updateRoom")
    public String updateRoom(@Valid @ModelAttribute("room") Room room,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "editRoom";
        }
        Room oldRoom = roomService.getRoom(room.getId()); // lấy bản cũ từ DB
        if (room.getImageFile() != null && !room.getImageFile().isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(room.getImageFile());
            room.setImage(imageUrl);
        } else {
            room.setImage(oldRoom.getImage()); // giữ lại ảnh cũ
        }
        roomService.updateRoom(room);
        return "redirect:/RoomController/viewRooms";
    }

    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") int id) {
        roomService.deleteRoom(id);
        return "redirect:/RoomController/viewRooms";
    }
}
