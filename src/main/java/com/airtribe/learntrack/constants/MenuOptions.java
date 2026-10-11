package com.airtribe.learntrack.constants;

public class MenuOptions {
    // same value in every menu: 0 exits the app or goes back to the main menu
    public static final int EXIT = 0;
    public static final int BACK = 0;

    // main menu
    public static final int STUDENT_MANAGEMENT = 1;
    public static final int COURSE_MANAGEMENT = 2;
    public static final int ENROLLMENT_MANAGEMENT = 3;

    // student menu
    public static final int ADD_STUDENT = 1;
    public static final int VIEW_STUDENTS = 2;
    public static final int SEARCH_STUDENT = 3;
    public static final int UPDATE_STUDENT = 4;
    public static final int DEACTIVATE_STUDENT = 5;

    // course menu
    public static final int ADD_COURSE = 1;
    public static final int VIEW_COURSES = 2;
    public static final int ACTIVATE_COURSE = 3;
    public static final int DEACTIVATE_COURSE = 4;

    // enrollment menu
    public static final int ENROLL_STUDENT = 1;
    public static final int VIEW_ENROLLMENTS = 2;
    public static final int COMPLETE_ENROLLMENT = 3;
    public static final int CANCEL_ENROLLMENT = 4;

    private MenuOptions() {
    }
}
