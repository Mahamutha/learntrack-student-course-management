package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;

public class CourseService {
    private CourseRepository courseRepository = new CourseRepository();

    public Course addCourse(String courseName, String description, int durationInWeeks) throws InvalidInputException {
        courseName = InputValidator.requireNonEmpty(courseName, "Course name");
        description = InputValidator.requireNonEmpty(description, "Description");
        if (durationInWeeks <= 0){
            throw new InvalidInputException("Duration must be greater than 0.");
        }
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        courseRepository.save(course);
        return course;
    }

    public ArrayList<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course with ID " + id + " not found.");
        }
        return course;
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(true);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(false);
    }
}
