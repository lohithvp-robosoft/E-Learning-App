package com.robosoft.elearning.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoft.elearning.model.ContentType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentResponse {
    private Long id;
    private String heading;
    private ContentType contentType;
    private String contentImg;
    private String info;
    private String videoUrl;
    private String thumbnail;
    private String audioUrl;
    private boolean userLiked;


    public ContentResponse(Long id, String heading, ContentType contentType, String contentImg, String info, String videoUrl, String thumbnail, String audioUrl, boolean userLiked) {
        this.id = id;
        this.heading = heading;
        this.contentType = contentType;
        this.contentImg = contentImg;
        this.info = info;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
        this.audioUrl = audioUrl;
        this.userLiked=userLiked;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }
}
