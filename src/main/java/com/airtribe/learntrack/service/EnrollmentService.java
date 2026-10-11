package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;

public class EnrollmentService {
    private ArrayList<Enrollment> enrollments = new ArrayList<>();
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId) throws EntityNotFoundException, InvalidInputException {
        // Validate student and course existence
        Student student = studentService.findStudentById(studentId);
        if (!student.isActive()) {
            throw new InvalidInputException("Student with ID " + studentId + " is not active.");
        }

        Course course = courseService.findCourseById(courseId);
        if (!course.isActive()) {
            throw new InvalidInputException("Course with ID " + courseId + " is not active.");
        }

        // Check if the student is already enrolled in the course
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && enrollment.getCourseId() == courseId && enrollment.getStatus().equals("ACTIVE")) {
                throw new InvalidInputException("Student with ID " + studentId + " is already enrolled in course with ID " + courseId);
            }
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        enrollments.add(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        // Validate student existence
        studentService.findStudentById(studentId);

        ArrayList<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Enrollment with ID " + id + " not found.");
    }

    public void completeEnrollment(int id) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(id);
        enrollment.setStatus("COMPLETED");
    }

    public void cancelEnrollment(int id) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(id);
        enrollment.setStatus("CANCELLED");
    }
}
