package com.robosoft.elearning.dto.response;

import java.util.List;

public class PaginatedContentResponseDTO {
    private List<ContentResponse> content;
    private Long lessonId;
    private String heading;

    private int totalPages;
    private int currentPage;

    public PaginatedContentResponseDTO(Long lessonId, String heading, List<ContentResponse> content, int totalPages, int currentPage) {
        this.lessonId=lessonId;
        this.heading=heading;
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }


    public PaginatedContentResponseDTO(List<ContentResponse> contentDTOs, int totalPages, int currentPage, Long lessonId, String heading) {
        this.lessonId=lessonId;
        this.heading=heading;
        this.currentPage=currentPage;
        this.content=contentDTOs;
        this.totalPages=totalPages;
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
}
