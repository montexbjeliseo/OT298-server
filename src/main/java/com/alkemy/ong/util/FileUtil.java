package com.alkemy.ong.util;

import com.alkemy.ong.exception.FileConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class FileUtil {

    @Autowired
    private static MessageSource messageSource;

    public static File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new FileConversionException(messageSource.getMessage("file.conversionError", null, Locale.US));
        }
        return convertedFile;
    }

    public static String createFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(' ', '_');
    }
}
