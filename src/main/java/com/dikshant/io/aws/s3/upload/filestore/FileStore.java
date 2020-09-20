package com.dikshant.io.aws.s3.upload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Component
public class FileStore {
    private Logger logger = LoggerFactory.getLogger(FileStore.class);

    private final AmazonS3 amazonS3;

    @Autowired
    public FileStore(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach((objectMetadata::addUserMetadata));
            }
        });
        objectMetadata.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);

        try {
            long startTime = System.currentTimeMillis();
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
            logger.info("Successfully uploaded the image [{}] to S3 in {} ms",
                    fileName, (System.currentTimeMillis() - startTime));
        } catch (AmazonServiceException e) {
            throw new IllegalArgumentException("Failed to upload " + fileName + " image to Amazon s3", e);
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public byte[] download(String path, String key) {
        try {
            long startTime = System.currentTimeMillis();
            S3Object s3Object = amazonS3.getObject(path, key);
            logger.info("Downloaded image for userId : [{}] in {} ms",
                    path.split("/")[1], (System.currentTimeMillis() - startTime));
            return IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalArgumentException("Failed to download image from Amazon s3", e);
        }
    }
}
