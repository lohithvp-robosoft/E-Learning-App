package com.robosoft.elearning.util;

import com.robosoft.elearning.config.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class FileStorageUtil {

    @Autowired
    private AwsConfig awsConfig;

    @Autowired
    private S3Client s3Client;

    public String storeFile(MultipartFile file, String folder, Long objectId) throws IOException {
        validateFolder(folder);

        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);

        String fileName = generateFileName(objectId, fileExtension);
        String s3Key = buildS3Key(folder, fileName);

        String contentType = determineContentType(fileExtension);

        try (InputStream inputStream = file.getInputStream()) {
            uploadToS3(inputStream, file.getSize(), s3Key, contentType);
        }

        return buildFileUrl(s3Key);
    }

    private void validateFolder(String folder) {
        if (folder == null || folder.isEmpty()) {
            throw new IllegalArgumentException("Folder name cannot be empty");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName != null) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    private String generateFileName(Long objectId, String fileExtension) {
        return objectId + "-" + UUID.randomUUID() + fileExtension;
    }

    private String buildS3Key(String folder, String fileName) {
        return folder + "/" + fileName;
    }

    private String determineContentType(String fileExtension) {
        String contentType = "application/octet-stream";

        if (fileExtension != null) {
            contentType = getImageContentType(fileExtension);
            if (contentType.equals("application/octet-stream")) {
                contentType = getAudioContentType(fileExtension);
            }
            if (contentType.equals("application/octet-stream")) {
                contentType = getVideoContentType(fileExtension);
            }
        }

        return contentType;
    }

    private String getImageContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".bmp":
                return "image/bmp";
            case ".webp":
                return "image/webp";
            default:
                return "application/octet-stream";
        }
    }

    private String getAudioContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case ".mp3":
                return "audio/mpeg";
            case ".wav":
                return "audio/wav";
            case ".aac":
                return "audio/aac";
            case ".ogg":
                return "audio/ogg";
            default:
                return "application/octet-stream";
        }
    }

    private String getVideoContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case ".mp4":
                return "video/mp4";
            case ".avi":
                return "video/x-msvideo";
            case ".mov":
                return "video/quicktime";
            case ".mkv":
                return "video/x-matroska";
            case ".webm":
                return "video/webm";
            default:
                return "application/octet-stream";
        }
    }

    private void uploadToS3(InputStream inputStream, long fileSize, String s3Key, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsConfig.bucketName())
                .key(s3Key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));
    }

    private String buildFileUrl(String s3Key) {
        return "https://" + awsConfig.bucketName() + ".s3." + awsConfig.getAwsRegion() + ".amazonaws.com/" + s3Key;
    }
}
