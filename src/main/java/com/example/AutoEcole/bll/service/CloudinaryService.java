package com.example.AutoEcole.bll.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public interface CloudinaryService {
    Map<String, String> upload(MultipartFile file) throws IOException;

    void delete(String publicId) throws IOException;
//    String uploadFile(MultipartFile file) throws IOException;
//    String saveFile(MultipartFile file) throws IOException;
//    Resource getFileAsResource(String fileName) throws IOException, FileNotFoundException;
}
