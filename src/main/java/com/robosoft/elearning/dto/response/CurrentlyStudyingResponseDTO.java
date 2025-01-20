package com.robosoft.elearning.dto.response;

public class CurrentlyStudyingResponseDTO {
    private String subjectName;
    private String subjectIcon;
    private String currentChapterName;
    private Double completedPercentage;

    public CurrentlyStudyingResponseDTO(String subjectName, String subjectIcon, String chapterName, Double completedChapterInPercentage) {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectIcon() {
        return subjectIcon;
    }

    public void setSubjectIcon(String subjectIcon) {
        this.subjectIcon = subjectIcon;
    }

    public String getCurrentChapterName() {
        return currentChapterName;
    }

    public void setCurrentChapterName(String currentChapterName) {
        this.currentChapterName = currentChapterName;
    }

    public Double getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(Double completedPercentage) {
        this.completedPercentage = completedPercentage;
    }
}
