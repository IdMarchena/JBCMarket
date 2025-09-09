package com.afk.backend.control.service.impl;

import com.afk.backend.control.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Override
    public String guardarImagenRedimensionada(MultipartFile archivo, String uploadDir, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(archivo.getInputStream());
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();

            String fileName = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, archivo.getBytes());

            String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
            ImageIO.write(resizedImage, formatName, path.toFile());

            return path.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }
    }
}
