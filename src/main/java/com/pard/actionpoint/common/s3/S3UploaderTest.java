package com.pard.actionpoint.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Profile("test")
@Component
public class S3UploaderTest extends S3Uploader {
    public S3UploaderTest(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    @Override
    public String upload(MultipartFile multipartFile, String dirName){
        return "https://fake-url.com/test.jpg";
    }
}