package com.lucifer.gada.electronics.services.Impl;

import com.lucifer.gada.electronics.exceptions.BadApiRequest;
import com.lucifer.gada.electronics.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile img, String path) throws IOException {
        String originalFilename = img.getOriginalFilename();
        logger.info("originalFilename: {}", originalFilename);
        String filename = UUID.randomUUID().toString();
        logger.info("UUID filename : {}", filename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        logger.info("extension {}",extension);
        String fileNameWithExtension = filename+extension;
        logger.info("fileNameWithExtension: {}",fileNameWithExtension);
        String fullPathWithFileName = path+ File.separator+fileNameWithExtension;
        logger.info("fullPathWithFileName", fullPathWithFileName);

        if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")){

            File folder = new File(path);

            if(!folder.exists()){
                folder.mkdirs();
            }
            logger.info("folder: {}",folder);
            //upload
            Files.copy(img.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else {
            throw new BadApiRequest("File with extension " +extension+ " not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
