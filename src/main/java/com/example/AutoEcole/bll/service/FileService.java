package com.example.AutoEcole.bll.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface FileService {
    String saveFile(MultipartFile file) throws IOException;
    Resource getFileAsResource(String fileName) throws IOException, FileNotFoundException;
}
