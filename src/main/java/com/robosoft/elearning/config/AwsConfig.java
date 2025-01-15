package com.robosoft.elearning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;

    @Value("${aws.secret.access.key}")
    private String awsSecretAccessKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

//    @Bean
//    public BasicAWSCredentials awsCredentials() {
//        return new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
//    }

    @Bean
    public AwsBasicCredentials awsBasicCredentials() {
        return AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials()))
                .build();
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }

    @Bean
    public String getAwsRegion() {
        return awsRegion;
    }
}