package com.robosoft.elearning.dto.request;

import java.util.List;

public class AssignSubjectRequestDTO {

    private List<Long> subjectIds;


    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
