package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MediaBasicDTO;
import com.alkemy.ong.service.IAmazonS3Service;
import com.alkemy.ong.util.FileUtil;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;


@Service
public class AmazonS3ServiceImpl implements IAmazonS3Service {

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.region}")
    private String region;

    @Value("${amazonProperties.url}")
    private String urlBucket;
    @Autowired
    private AmazonS3 s3Client;

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Override
    public String uploadFileBase64(String base64File, String imageName) throws Exception {
        String fileUrl = "";
        try {
            File file = convertBase64ToFile(base64File, imageName);
            String key = new Date().getTime() + "-" + imageName.replace(" ", "_");
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/png");
            s3Client.putObject(new PutObjectRequest(bucketName, key, file)
                    .withMetadata(objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl =  urlBucket.concat("/") + bucketName + "/" + key;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return fileUrl;
    }


    private File convertBase64ToFile(String base64File, String fileName) throws Exception {
        String[] base64Components = base64File.split(",");
        if (base64Components.length != 2) {
            throw new Exception("failed");
        }
        byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Components[1]);
        File fileConvert = new File(fileName);
        FileOutputStream f = new FileOutputStream(fileConvert);
        f.write(bytes);
        f.close();
        return fileConvert;
    }



    public MediaBasicDTO uploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            String fileName = FileUtil.createFileName(file);
            File fileObj = FileUtil.convertMultipartFileToFile(file);
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = s3Client.getUrl(bucketName, fileName).toString();
            fileObj.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new MediaBasicDTO(fileUrl);
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        s3Client.deleteObject(bucketName, fileName);
    }
}