package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;

public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
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
        for (Enrollment enrollment : enrollmentRepository.findByStudentId(studentId)) {
            if (enrollment.getCourseId() == courseId && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                throw new InvalidInputException("Student with ID " + studentId + " is already enrolled in course with ID " + courseId);
            }
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        // Validate student existence
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(id);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment with ID " + id + " not found.");
        }
        return enrollment;
    }

    public void completeEnrollment(int id) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(id);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE){
            throw new InvalidInputException("Enrollment " + id + " is already " + enrollment.getStatus() + ".");
        }
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }

    public void cancelEnrollment(int id) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(id);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE){
            throw new InvalidInputException("Enrollment " + id + " is already " + enrollment.getStatus() + ".");
        }
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
    }
}
