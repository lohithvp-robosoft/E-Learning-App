package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String contentImg;
    private String videoUrl;
    private String thumbnail;
    private String audioUrl;
    private String heading;

    @Column(name = "info", columnDefinition = "TEXT")
    private String info;

    @ManyToOne
    private Topic topic;

    private int pageNumber;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
// public void setTopic(Topic topic) {
      //  this.topic = topic;
    //}


    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", contentImg='" + contentImg + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", heading='" + heading + '\'' +
                ", info='" + info + '\'' +
                ", topic=" + topic +
                ", pageNumber=" + pageNumber +
                ", lesson=" + lesson +
                '}';
    }
}

