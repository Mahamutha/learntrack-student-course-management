package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("Welcome to " + AppConstants.APP_NAME);

        int choice;
        do {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("0. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case MenuOptions.STUDENT_MANAGEMENT -> studentMenu();
                case MenuOptions.COURSE_MANAGEMENT -> courseMenu();
                case MenuOptions.ENROLLMENT_MANAGEMENT -> enrollmentMenu();
                case MenuOptions.EXIT -> System.out.println("Goodbye!");
                default -> System.out.println(AppConstants.INVALID_OPTION);
            }
        } while (choice != MenuOptions.EXIT);
    }

    // ===== Student Management =====

    private static void studentMenu() {
        int choice;
        do {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student email and batch");
            System.out.println("5. Deactivate student");
            System.out.println("0. Back to main menu");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case MenuOptions.ADD_STUDENT -> handleAddStudent();
                case MenuOptions.VIEW_STUDENTS -> handleViewStudents();
                case MenuOptions.SEARCH_STUDENT -> handleSearchStudent();
                case MenuOptions.UPDATE_STUDENT -> handleUpdateStudent();
                case MenuOptions.DEACTIVATE_STUDENT -> handleDeactivateStudent();
                case MenuOptions.BACK -> System.out.println(AppConstants.RETURNING_TO_MAIN_MENU);
                default -> System.out.println(AppConstants.INVALID_OPTION);
            }
        } while (choice != MenuOptions.BACK);
    }

    private static void handleAddStudent() {
        String firstName = readText("First name: ", "First name");
        String lastName = readText("Last name: ", "Last name");
        String batch = readText("Batch: ", "Batch");
        System.out.print("Email (press Enter to skip): ");
        String email = scanner.nextLine().trim();

        Student student;
        if (email.isEmpty()) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added with ID " + student.getId() + ": " + student.getDisplayName());
    }

    private static void handleViewStudents() {
        ArrayList<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            printStudent(student);
        }
    }

    private static void handleSearchStudent() {
        int id = readInt("Enter student ID: ");
        try {
            printStudent(studentService.findStudentById(id));
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleUpdateStudent() {
        int id = readInt("Enter student ID: ");
        try {
            studentService.findStudentById(id);
            String email = readText("New email: ", "Email");
            String batch = readText("New batch: ", "Batch");
            studentService.updateStudent(id, email, batch);
            System.out.println("Student " + id + " updated.");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleDeactivateStudent() {
        int id = readInt("Enter student ID: ");
        try {
            studentService.deactivateStudent(id);
            System.out.println("Student " + id + " deactivated.");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void printStudent(Student student) {
        System.out.println(student.getId() + " | " + student.getDisplayName()
                + " | Email: " + student.getEmail() + " | Active: " + student.isActive());
    }

    // ===== Course Management =====

    private static void courseMenu() {
        int choice;
        do {
            System.out.println("\n===== COURSE MENU =====");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate course");
            System.out.println("4. Deactivate course");
            System.out.println("0. Back to main menu");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case MenuOptions.ADD_COURSE -> handleAddCourse();
                case MenuOptions.VIEW_COURSES -> handleViewCourses();
                case MenuOptions.ACTIVATE_COURSE -> handleActivateCourse();
                case MenuOptions.DEACTIVATE_COURSE -> handleDeactivateCourse();
                case MenuOptions.BACK -> System.out.println(AppConstants.RETURNING_TO_MAIN_MENU);
                default -> System.out.println(AppConstants.INVALID_OPTION);
            }
        } while (choice != MenuOptions.BACK);
    }

    private static void handleAddCourse() {
        String courseName = readText("Course name: ", "Course name");
        String description = readText("Description: ", "Description");
        int durationInWeeks = readInt("Duration in weeks: ");
        if (durationInWeeks <= 0) {
            System.out.println(AppConstants.ERROR_PREFIX + "Duration must be greater than 0.");
            return;
        }
        Course course = courseService.addCourse(courseName, description, durationInWeeks);
        System.out.println("Course added with ID " + course.getId() + ": " + course.getCourseName());
    }

    private static void handleViewCourses() {
        ArrayList<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course.getId() + " | " + course.getCourseName()
                    + " | " + course.getDescription()
                    + " | " + course.getDurationInWeeks() + " weeks"
                    + " | Active: " + course.isActive());
        }
    }

    private static void handleActivateCourse() {
        int id = readInt("Enter course ID: ");
        try {
            courseService.activateCourse(id);
            System.out.println("Course " + id + " activated.");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleDeactivateCourse() {
        int id = readInt("Enter course ID: ");
        try {
            courseService.deactivateCourse(id);
            System.out.println("Course " + id + " deactivated.");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    // ===== Enrollment Management =====

    private static void enrollmentMenu() {
        int choice;
        do {
            System.out.println("\n===== ENROLLMENT MENU =====");
            System.out.println("1. Enroll student in course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment as completed");
            System.out.println("4. Mark enrollment as cancelled");
            System.out.println("0. Back to main menu");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case MenuOptions.ENROLL_STUDENT -> handleEnrollStudent();
                case MenuOptions.VIEW_ENROLLMENTS -> handleViewEnrollments();
                case MenuOptions.COMPLETE_ENROLLMENT -> handleCompleteEnrollment();
                case MenuOptions.CANCEL_ENROLLMENT -> handleCancelEnrollment();
                case MenuOptions.BACK -> System.out.println(AppConstants.RETURNING_TO_MAIN_MENU);
                default -> System.out.println(AppConstants.INVALID_OPTION);
            }
        } while (choice != MenuOptions.BACK);
    }

    private static void handleEnrollStudent() {
        int studentId = readInt("Enter student ID: ");
        int courseId = readInt("Enter course ID: ");
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("Enrollment created with ID " + enrollment.getId() + ".");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleViewEnrollments() {
        int studentId = readInt("Enter student ID: ");
        try {
            ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for student " + studentId + ".");
                return;
            }
            for (Enrollment enrollment : enrollments) {
                System.out.println("Enrollment " + enrollment.getId()
                        + " | Course ID: " + enrollment.getCourseId()
                        + " | Date: " + enrollment.getEnrollmentDate()
                        + " | Status: " + enrollment.getStatus());
            }
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleCompleteEnrollment() {
        int id = readInt("Enter enrollment ID: ");
        try {
            enrollmentService.completeEnrollment(id);
            System.out.println("Enrollment " + id + " marked as " + EnrollmentStatus.COMPLETED + ".");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    private static void handleCancelEnrollment() {
        int id = readInt("Enter enrollment ID: ");
        try {
            enrollmentService.cancelEnrollment(id);
            System.out.println("Enrollment " + id + " marked as " + EnrollmentStatus.CANCELLED + ".");
        } catch (EntityNotFoundException e) {
            System.out.println(AppConstants.ERROR_PREFIX + e.getMessage());
        }
    }

    // ===== Input helpers =====

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return InputValidator.parseInt(scanner.nextLine());
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String readText(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                return InputValidator.requireNonEmpty(scanner.nextLine(), fieldName);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
