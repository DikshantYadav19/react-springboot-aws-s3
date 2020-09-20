package com.dikshant.io.aws.s3.upload.bucket;

public enum BucketName {

    PROFILE_IMAGE("dikshant-upload-image-s3");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
