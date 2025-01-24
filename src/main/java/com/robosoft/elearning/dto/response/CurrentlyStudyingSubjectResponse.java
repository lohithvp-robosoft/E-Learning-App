package com.robosoft.elearning.dto.response;

import java.util.List;

public class CurrentlyStudyingSubjectResponse {

    private Long subjectId;
    private String subjectName;
    private Long currentChapterId;
    private List<ChapterSummaryResponse> chapters;

    public CurrentlyStudyingSubjectResponse(Long subjectId, String subjectName, Long currentChapterId, List<ChapterSummaryResponse> chapters) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.currentChapterId = currentChapterId;
        this.chapters = chapters;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getCurrentChapterId() {
        return currentChapterId;
    }

    public void setCurrentChapterId(Long currentChapterId) {
        this.currentChapterId = currentChapterId;
    }

    public List<ChapterSummaryResponse> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterSummaryResponse> chapters) {
        this.chapters = chapters;
    }
}