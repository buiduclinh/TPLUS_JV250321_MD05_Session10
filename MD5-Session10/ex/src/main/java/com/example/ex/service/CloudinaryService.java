package com.example.ex.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            Map<String, Object> params = ObjectUtils.asMap(
                    "folder", "rooms", // Tạo thư mục "rooms" trên Cloudinary
                    "width", 800,      // Resize ảnh
                    "height", 600,
                    "crop", "fill"
            );
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            return uploadResult.get("secure_url").toString(); // Lấy URL ảnh
        } catch (IOException e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }
}
