package com.afk.backend.control.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    String guardarImagenRedimensionada(MultipartFile archivo, String uploadDir, int width, int height);
}
