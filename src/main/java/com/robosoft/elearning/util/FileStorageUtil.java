package com.robosoft.elearning.util;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import com.robosoft.elearning.config.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileStorageUtil {

    @Autowired
    private AwsConfig awsConfig; // Get AWS config which contains S3 client and bucket name

    @Autowired
    private S3Client s3Client;

    public String storeFile(MultipartFile file, String folder, Long objectId) throws IOException {
        // Validate if folder is not empty or null
        if (folder == null || folder.isEmpty()) {
            throw new IllegalArgumentException("Folder name cannot be empty");
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // Generate a unique file name with object ID and the file extension
        String fileName = objectId + "-" + UUID.randomUUID() + fileExtension;
        String s3Key = folder + "/" + fileName;

        // Determine content type based on the file extension
        String contentType = "application/octet-stream"; // Default type

        if (originalFileName != null) {
            // Image types
            if (fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fileExtension.equalsIgnoreCase(".png")) {
                contentType = "image/png";
            } else if (fileExtension.equalsIgnoreCase(".gif")) {
                contentType = "image/gif";
            } else if (fileExtension.equalsIgnoreCase(".bmp")) {
                contentType = "image/bmp";
            } else if (fileExtension.equalsIgnoreCase(".webp")) {
                contentType = "image/webp";

                // Audio types
            } else if (fileExtension.equalsIgnoreCase(".mp3")) {
                contentType = "audio/mpeg";
            } else if (fileExtension.equalsIgnoreCase(".wav")) {
                contentType = "audio/wav";
            } else if (fileExtension.equalsIgnoreCase(".aac")) {
                contentType = "audio/aac";
            } else if (fileExtension.equalsIgnoreCase(".ogg")) {
                contentType = "audio/ogg";

                // Video types
            } else if (fileExtension.equalsIgnoreCase(".mp4")) {
                contentType = "video/mp4";
            } else if (fileExtension.equalsIgnoreCase(".avi")) {
                contentType = "video/x-msvideo";
            } else if (fileExtension.equalsIgnoreCase(".mov")) {
                contentType = "video/quicktime";
            } else if (fileExtension.equalsIgnoreCase(".mkv")) {
                contentType = "video/x-matroska";
            } else if (fileExtension.equalsIgnoreCase(".webm")) {
                contentType = "video/webm";
            }
        }

        // Upload the file with the correct content type
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsConfig.bucketName())
                    .key(s3Key)
                    .contentType(contentType) // Set content type here
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
        }

        // Generate the URL of the uploaded file (assuming the bucket is public)
        String fileUrl = "https://" + awsConfig.bucketName() + ".s3." + awsConfig.getAwsRegion() + ".amazonaws.com/" + s3Key;

        return fileUrl;
    }
}
