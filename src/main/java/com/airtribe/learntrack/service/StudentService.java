package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;

public class StudentService {
    private ArrayList<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) {
        return addStudent(firstName, lastName, null, batch);
    }

    public ArrayList<Student> listStudents() {
        return students;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student with ID " + id + " not found.");
    }

    public void updateStudent(int id, String email, String batch) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setEmail(email);
        student.setBatch(batch);
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
    }

    public void removeStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        students.remove(student);
    }
}
