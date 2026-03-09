package com.mosquito.mosquitowiki.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${app.file.upload-dir}")
    private String uploadDir;

    public String save(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
        return "/images/" + filename;
    }
}
