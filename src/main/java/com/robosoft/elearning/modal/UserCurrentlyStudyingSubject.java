package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class UserCurrentlyStudyingSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Chapter chapter;

    // Track lesson progress - map of lesson ID and progress (completed: true/false)
    @ElementCollection
    private Map<Long, Boolean> lessonProgress = new HashMap<>();

    private int completedLessonsCount;

    private boolean completedChapter;

    private boolean isStudying;

    // Update lesson progress and check if the chapter is completed
    public void updateLessonProgress(Long lessonId, boolean isCompleted) {
        // Update the lesson progress
        lessonProgress.put(lessonId, isCompleted);

        // Update the count of completed lessons
        if (isCompleted) {
            completedLessonsCount++;
        } else {
            completedLessonsCount--;
        }

        // Check if the chapter is completed
        checkChapterCompletion();
    }

    // Check if the user has completed the chapter based on lessons
    private void checkChapterCompletion() {
        // If the number of completed lessons matches the total lessons in the chapter, mark chapter as completed
        if (completedLessonsCount == chapter.getLessons().size()) {
            completedChapter = true;
        } else {
            completedChapter = false;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Map<Long, Boolean> getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(Map<Long, Boolean> lessonProgress) {
        this.lessonProgress = lessonProgress;
    }

    public int getCompletedLessonsCount() {
        return completedLessonsCount;
    }

    public void setCompletedLessonsCount(int completedLessonsCount) {
        this.completedLessonsCount = completedLessonsCount;
    }

    public boolean isCompletedChapter() {
        return completedChapter;
    }

    public void setCompletedChapter(boolean completedChapter) {
        this.completedChapter = completedChapter;
    }

    public boolean isStudying() {
        return isStudying;
    }

    public void setIsStudying(boolean studying) {
        isStudying = studying;
    }

}

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    private Subject subject;
//
//    @ManyToOne
//    private Chapter currentChapter;
//
//    @ManyToOne
//    private Lesson currentLesson;
//
//    @OneToMany(mappedBy = "userCurrentlyStudyingSubject", cascade = CascadeType.ALL)
//    private List<CompletedChapter> completedChapters;
//
//    private Double completedChapterInPercentage;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date startedAt;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//
//    public Chapter getCurrentChapter() {
//        return currentChapter;
//    }
//
//    public void setCurrentChapter(Chapter currentChapter) {
//        this.currentChapter = currentChapter;
//    }
//
//    public Lesson getCurrentLesson() {
//        return currentLesson;
//    }
//
//    public void setCurrentLesson(Lesson currentLesson) {
//        this.currentLesson = currentLesson;
//    }
//
//    public List<CompletedChapter> getCompletedChapters() {
//        return completedChapters;
//    }
//
//    public void setCompletedChapters(List<CompletedChapter> completedChapters) {
//        this.completedChapters = completedChapters;
//    }
//
//    public Double getCompletedChapterInPercentage() {
//        return completedChapterInPercentage;
//    }
//
//    public void setCompletedChapterInPercentage(Double completedChapterInPercentage) {
//        this.completedChapterInPercentage = completedChapterInPercentage;
//    }
//}
