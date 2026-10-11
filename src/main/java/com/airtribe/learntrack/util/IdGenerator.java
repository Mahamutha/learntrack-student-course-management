package com.airtribe.learntrack.util;

public class IdGenerator {
    private static int studentIdCounter = 0;
    private static int courseIdCounter = 0;
    private static int enrollmentIdCounter = 0;

    private IdGenerator() {
    }

    public static int getNextStudentId() {
        studentIdCounter++;
        return studentIdCounter;
    }

    public static int getNextCourseId() {
        courseIdCounter++;
        return courseIdCounter;
    }

    public static int getNextEnrollmentId() {
        enrollmentIdCounter++;
        return enrollmentIdCounter;
    }
}
