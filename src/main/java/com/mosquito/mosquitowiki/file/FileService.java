package com.mosquito.mosquitowiki.file;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public String save(MultipartFile file) throws IOException {
        log.info("Saving file: {}", file.getOriginalFilename());
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        log.info("Saving file to {}", path);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
        return "/images/" + filename;
    }
}
