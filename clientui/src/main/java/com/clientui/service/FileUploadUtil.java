package com.clientui.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                byte[] file) throws IOException {

        System.out.println("\n save file ");
        Path uploadPath = Paths.get(uploadDir);

        System.out.println("\n uploadPath   " + uploadPath.toString());


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        /**
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(file);

        }
         */


    }

    /*
     public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
     */
}