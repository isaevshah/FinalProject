package com.example.ezlearning.business.entity;

import com.example.ezlearning.auth.entity.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "education", schema = "public", catalog = "finalproject")
public class Education {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "education_id")
    private Long educationId;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "course_id")
    private Long courseId;
    @Basic
    @Column(name = "user_id")
    private Long userId;

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education that = (Education) o;
        return Objects.equals(educationId, that.educationId) && Objects.equals(date, that.date) && Objects.equals(courseId, that.courseId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educationId, date, courseId, userId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses courses;
}
