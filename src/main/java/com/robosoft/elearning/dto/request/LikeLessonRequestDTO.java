package com.robosoft.elearning.dto.request;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class LikeLessonRequestDTO {
    private Long lessonId;

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}