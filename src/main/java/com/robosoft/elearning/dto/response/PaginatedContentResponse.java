package com.robosoft.elearning.dto.response;

import java.util.List;

public class PaginatedContentResponse {
    private List<ContentResponse> content;
    private int totalPages;
    private int currentPage;
    private Long lessonId;
    private String heading;
    private Long topicId;
    private Long lessonIndex;

    public PaginatedContentResponse(List<ContentResponse> content,Long lessonIndex, int totalPages, int currentPage, Long lessonId, String heading, Long topicId) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.lessonId = lessonId;
        this.lessonIndex=lessonIndex;
        this.heading = heading;
        this.topicId = topicId;
    }

    public List<ContentResponse> getContent() {
        return content;
    }

    public void setContent(List<ContentResponse> content) {
        this.content = content;
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

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Long lessonIndex) {
        this.lessonIndex = lessonIndex;
    }
}