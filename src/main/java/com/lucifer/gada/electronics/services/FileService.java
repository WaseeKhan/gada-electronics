package com.lucifer.gada.electronics.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(MultipartFile img, String path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;
}
