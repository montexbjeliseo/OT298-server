package com.alkemy.ong.util.controller;

import com.alkemy.ong.dto.MediaBasicDTO;
import com.alkemy.ong.service.IAmazonS3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
@Tag(name = "AWS S3")
public class AmazonS3Controller {

    @Autowired
    private IAmazonS3Service amazonS3Service;

    @PostMapping("/uploadFile")
    public ResponseEntity<MediaBasicDTO> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity.ok(amazonS3Service.uploadFile(file));
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Void> deleteFile(@RequestPart(value = "url") String fileUrl) {
        amazonS3Service.deleteFile(fileUrl);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
