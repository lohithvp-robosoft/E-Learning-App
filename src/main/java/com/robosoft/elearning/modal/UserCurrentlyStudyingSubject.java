package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserCurrentlyStudyingSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    private int completedLessonsCount;

    private boolean completedChapter;

    private boolean isStudying;

    public void updateLessonProgress(Lesson lesson, boolean isCompleted) {
        LessonProgress progress = lessonProgresses.stream()
                .filter(lp -> lp.getLesson().getId().equals(lesson.getId()))
                .findFirst()
                .orElse(new LessonProgress());

        progress.setLesson(lesson);
        progress.setCompleted(isCompleted);

        if (!lessonProgresses.contains(progress)) {
            lessonProgresses.add(progress);
        }

        completedLessonsCount = (int) lessonProgresses.stream().filter(LessonProgress::isCompleted).count();
        checkChapterCompletion();
    }

    private void checkChapterCompletion() {
        if (chapter == null || chapter.getLessons() == null || chapter.getLessons().isEmpty()) {
            completedChapter = false;
            return;
        }
        completedChapter = (completedLessonsCount == chapter.getLessons().size());
    }



    public boolean isStudying() {
        return isStudying;
    }

    public void setIsStudying(boolean studying) {
        isStudying = studying;
    }

    public boolean isCompletedChapter() {
        return completedChapter;
    }

    public void setCompletedChapter(boolean completedChapter) {
        this.completedChapter = completedChapter;
    }

    public int getCompletedLessonsCount() {
        return completedLessonsCount;
    }

    public void setCompletedLessonsCount(int completedLessonsCount) {
        this.completedLessonsCount = completedLessonsCount;
    }

    public List<LessonProgress> getLessonProgresses() {
        return lessonProgresses;
    }

    public void setLessonProgresses(List<LessonProgress> lessonProgresses) {
        this.lessonProgresses = lessonProgresses;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
