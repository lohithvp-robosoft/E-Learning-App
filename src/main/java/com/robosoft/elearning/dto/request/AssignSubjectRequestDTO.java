package com.robosoft.elearning.dto.request;

import java.util.List;

public class AssignSubjectRequestDTO {
    private Long userId;
    private List<Long> subjectIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
