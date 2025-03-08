class Enrollment {
    private Student student;
    private Course course;
    private Professor professor;

    // Constructor
    public Enrollment(Student student, Course course, Professor professor) {
        this.student = student;
        this.course = course;
        this.professor = professor;
    }

    // Method to display enrollment details
    public void displayEnrollmentInfo() {
        System.out.println("Enrollment Information:");
        student.displayStudentInfo();
        course.displayCourseInfo();
        professor.displayProfessorInfo();
    }
}