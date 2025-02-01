package com.robosoft.elearning.util;

import com.robosoft.elearning.dto.request.LessonRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapperUtil {


    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    public UserDetailResponse convertUserToUserDetailResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setId(user.getId());
        userDetailResponse.setEmail(user.getEmail());
        userDetailResponse.setUserName(user.getUserName());
        userDetailResponse.setProfileImageUrl(user.getProfileImageUrl());
        userDetailResponse.setCompleterChapterInPercentage(user.getChaptersCompletedInPercentage());

        UserTestResult testResult = user.getUserTestResult();
        if (testResult != null) {
            UserTestResultResponse testResultResponse = new UserTestResultResponse(
                    testResult.getId(),
                    testResult.getAverageScore(),
                    testResult.getHighestScore()
            );
            userDetailResponse.setTestResult(testResultResponse);
        } else {
            userDetailResponse.setTestResult(null);
        }

        userDetailResponse.setNotificationEnabled(user.isNotificationEnabled());
        return userDetailResponse;
    }

    public TestResponse convertToTestResponse(Test test) {
        return new TestResponse(
                test.getId(),
                test.getTestIcon(),
                test.getHeading(),
                test.getLevel(),
                test.getTotalTime(),
                test.getLesson() != null ? test.getLesson().getLessonName() : null,
                test.getQuestions().size(),
                "You have "+ test.getTotalTime()+" minutes to answer all "+test.getQuestions().size()+" questions.",
                test.getCreatedAt(),
                test.getUpdatedAt()
        );
    }

    public QuestionResponse convertToQuestionResponse(Question question) {
        if (question == null) {
            return null;
        }

        return new QuestionResponse(
                question.getId(),
                question.getQuestionStatement(),
                question.getOptions(),
                question.getQuestionImageUrl()
//                previouslySelectedOption,
//                currentQuestionIndex,
//                totalNoOfQuestion,
//                lessonIndex,
//                lessonName,
//                chapterIndex
        );
    }

    public List<QuestionSetResponse> convertToQuestionSetResponse(List<Question> questions, UserTestProgress userTestProgress) {
        if (questions == null || userTestProgress == null) {
            return List.of();
        }

        return questions.stream()
                .map(question -> new QuestionSetResponse(
                        question.getId(),
                        question.getQuestionStatement(),

                        userTestProgress.getSelectedAnswers().containsKey(question.getId())
                ))
                .toList();
    }

    public TestSubmitResponse convertToTestSubmitResponse(int securedMarksInPercentage, int totalAttemptedQuestions, int totalQuestions) {
        String remarksComment;
        String remarkSubComment;

        if (totalAttemptedQuestions < totalQuestions) {
            remarksComment = "Oooops";
            remarkSubComment = "You ran out of time.\nYour test has been submitted by default.";
        } else if (securedMarksInPercentage >= 90) {
            remarksComment = "Excellent";
            remarkSubComment = "Outstanding performance!";
        } else if (securedMarksInPercentage >= 75) {
            remarksComment = "Good";
            remarkSubComment = "Keep up the good work!";
        } else if (securedMarksInPercentage >= 50) {
            remarksComment = "Average";
            remarkSubComment = "You can do better!";
        } else {
            remarksComment = "Poor";
            remarkSubComment = "Needs significant improvement.";
        }

        return new TestSubmitResponse(
                securedMarksInPercentage,
                totalAttemptedQuestions,
                totalQuestions,
                remarksComment,
                remarkSubComment
        );
    }

    public NotificationResponse toNotificationResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getTimestamp()
        );
    }

    public List<UserCurrentlyStudyingResponse> convertToUserCurrentlyStudyingResponseList(List<UserCurrentlyStudying> subjects) {
        return subjects.stream()
                .map(currentlyStudying -> new UserCurrentlyStudyingResponse(
                        currentlyStudying.getId(),
                        currentlyStudying.getSubject().getId(),
                        currentlyStudying.getSubject() != null ? currentlyStudying.getSubject().getSubjectName() : null,
                        currentlyStudying.getCompletedChapterInPercentage(),
                        currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getChapterName() : null,
                        currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getId() : null,
                        currentlyStudying.getCurrentLesson() != null ? currentlyStudying.getCurrentLesson().getId() : null,
                        currentlyStudying.getCurrentLesson() != null ? currentlyStudying.getCurrentLesson().getLessonName() : null,
                        currentlyStudying.getCurrentTopic() != null ? currentlyStudying.getCurrentTopic().getHeading() : null,
                        currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getChapterImg() : null,
                        currentlyStudying.getCompletedLessonInPercentage()
                ))
                .collect(Collectors.toList());

    }

    public UserCurrentlyStudyingResponse convertToUserCurrentlyStudyingResponse(UserCurrentlyStudying currentlyStudying) {
        return new UserCurrentlyStudyingResponse(
                currentlyStudying.getId(),
                currentlyStudying.getSubject().getId(),
                currentlyStudying.getSubject() != null ? currentlyStudying.getSubject().getSubjectName() : null,
                currentlyStudying.getCompletedChapterInPercentage(),
                currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getChapterName() : null,
                currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getId() : null,
                currentlyStudying.getCurrentLesson() != null ? currentlyStudying.getCurrentLesson().getId() : null,
                currentlyStudying.getCurrentLesson() != null ? currentlyStudying.getCurrentLesson().getLessonName() : null,
                currentlyStudying.getCurrentTopic() != null ? currentlyStudying.getCurrentTopic().getHeading() : null,
                currentlyStudying.getCurrentChapter() != null ? currentlyStudying.getCurrentChapter().getChapterImg() : null,
                currentlyStudying.getCompletedLessonInPercentage()
        );
    }

    public UserLikedTopicResponse convertToUserLikedTopicResponse(Topic topic, Integer chapterIndex, Integer pageNo){

        return new UserLikedTopicResponse(
                topic.getId(),
                topic.getHeading(),
                topic.getSubHeading(),
                topic.getLevel(),
                topic.getIcon(),
                topic.getLesson().getLessonName(),
                chapterIndex,
                pageNo
        );
    }

    public ChapterResponse convertChapterToChapterResponseWithoutLessons(Chapter chapter) {
        if (chapter == null) {
            return null;
        }
        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setChapterName(chapter.getChapterName());
        chapterResponse.setChapterImg(chapter.getChapterImg());

        return chapterResponse;
    }

    public SubjectResponse convertSubjectToSubjectResponse(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setSubjectName(subject.getSubjectName());
        subjectResponse.setSubjectIcon(subject.getSubjectIcon());
        return subjectResponse;
    }

    public ChapterResponse convertChapterToChapterResponse(Chapter chapter) {
        if (chapter == null) {
            return null;
        }
        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setChapterName(chapter.getChapterName());
        chapterResponse.setChapterImg(chapter.getChapterImg());

        List<LessonResponse> lessonResponses = new ArrayList<>();
        if (chapter.getLessons() != null) {
            for (Lesson lesson : chapter.getLessons()) {
                LessonResponse lessonResponse = new LessonResponse();
                Long lessonIndex = (long) lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;

                lessonResponse.setLessonIndex(lessonIndex);
                lessonResponse.setLessonName(lesson.getLessonName());
                lessonResponse.setLessonImg(lesson.getLessonImg());
                lessonResponse.setLevel(lesson.getLevel());
                lessonResponse.setHeading(lesson.getHeading());
                lessonResponse.setSubheading(lesson.getSubheading());
                lessonResponses.add(lessonResponse);
            }
        }
        return chapterResponse;
    }


    public LessonResponse convertLessonToLessonResponse(Lesson lesson) {
        if (lesson == null) {
            return null;
        }
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setLessonName(lesson.getLessonName());
        lessonResponse.setLessonImg((lesson.getLessonImg()));
        return lessonResponse;
    }

    public TopicResponse convertTopicToTopicResponse(Topic topic) {
        if (topic == null) {
            return null;
        }
        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setSubjectId(topic.getLesson().getChapter().getSubject().getId());
        topicResponse.setId(topic.getId());
        topicResponse.setHeading(topic.getHeading());
        topicResponse.setSubHeading(topic.getSubHeading());
        List<ContentResponse> contentResponseList = new ArrayList<>();
        topicResponse.setContent(contentResponseList);
        topicResponse.setLevel(topic.getLevel());

        return topicResponse;
    }


    public void updateLessonFromRequest(LessonRequest lessonRequest, Lesson lesson) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        lesson.setLessonName(lessonRequest.getLessonName());
        lesson.setChapter(chapter);
    }

    public Lesson convertLessonRequestToLesson(LessonRequest lessonRequest) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.getLessonName());
        lesson.setChapter(chapter);

        return lesson;
    }

}
