package com.robosoft.elearning.dto.response;

import java.util.List;

public class TestResponseList {
    private Boolean isLessonCompleted;
    private List<TestResponse> testList;

    public TestResponseList(Boolean isLessonCompleted, List<TestResponse> testList) {
        this.isLessonCompleted = isLessonCompleted;
        this.testList = testList;
    }

    public Boolean getLessonCompleted() {
        return isLessonCompleted;
    }

    public void setLessonCompleted(Boolean lessonCompleted) {
        isLessonCompleted = lessonCompleted;
    }

    public List<TestResponse> getTestList() {
        return testList;
    }

    public void setTestList(List<TestResponse> testList) {
        this.testList = testList;
    }
}
