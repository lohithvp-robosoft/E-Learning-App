package com.robosoft.elearning.dto.response;

import java.util.List;

public class PaginatedContentResponseDTO {
    private List<ContentResponseDTO> content;
    private int totalPages;
    private int currentPage;

    public PaginatedContentResponseDTO(List<ContentResponseDTO> content, int totalPages, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<ContentResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<ContentResponseDTO> content) {
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
