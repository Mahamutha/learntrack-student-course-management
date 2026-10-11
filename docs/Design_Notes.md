# Design Notes

## Why ArrayList and not an array

Arrays have a fixed size, and I dont know how many students or courses the admin is going to add. ArrayList just grows when I call add(), and things like remove() and isEmpty() are already there. Removing a student from an array would mean shifting everything after it myself.

The ArrayLists are inside the repository classes (StudentRepository, CourseRepository, EnrollmentRepository). They only save and find data, and the services use them for everything else.

## Where I used static

IdGenerator - the ID counters are static so there is only one counter for the whole program. If each object had its own counter, they would all start at 0 and i would end up with two students with ID 1. The methods are static too, so I just call IdGenerator.getNextStudentId(). The constructor is private since there is no reason to create an IdGenerator object.

InputValidator - same idea. parseInt() and requireNonEmpty() dont store anything, they just check the input, so static helpers made sense.

Main - main() is static, so the scanner, the services and the menu methods it calls have to be static as well.

MenuOptions and AppConstants - only public static final fields, like the menu numbers and the messages that repeat in Main. Static because they belong to the class, final because they never change.

EnrollmentStatus is an enum, so ACTIVE, COMPLETED and CANCELLED are fixed constants too. Before that i used Strings, and a typo like "Active" would compile fine but never match.

The lists inside the repositories are not static. Each service creates its own repository, and I pass the same StudentService and CourseService into EnrollmentService. If EnrollmentService made its own new StudentService, it would get a new empty repository and never find any student.

## Where I used inheritance

Person has the common fields (id, first name, last name, email). Student and Trainer extend it and only add their own fields: batch and active for Student, expertise for Trainer. So I didn't have to repeat the same fields and getters in both.

Since Person's fields are private, the child constructors pass them up using super(). getDisplayName() is overridden in Student and Trainer to add the batch or expertise, and it reuses super.getDisplayName() for the name part.

I didnt make Course or Enrollment extend Person, because a course isnt a person. Enrollment just keeps the student ID and course ID. EnrollmentService also doesn't extend StudentService, it only uses it.
