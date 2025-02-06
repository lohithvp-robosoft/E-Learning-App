package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.model.ContentType;

import java.util.List;

public class LessonContentResponse {
    private String lessonName;
    private ContentType contentType;
    private String contentUrl;
    private String audioUrl;
    private int currentPage;
    private int totalPages;
    private boolean liked;
    private List<PageNavigationResponse> pages;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public List<PageNavigationResponse> getPages() {
        return pages;
    }

    public void setPages(List<PageNavigationResponse> pages) {
        this.pages = pages;
    }
}