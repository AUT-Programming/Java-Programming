public class Main {
    public static void main(String[] args) {
        // Creating Student, Course, and Professor objects
        Student student1 = new Student("S1001", "Alice Johnson", "Computer Science");
        double[] grades1 = {90, 85, 92, 88, 95}; // Example grades for Alice
        student1.setGrades(grades1);

        Course course1 = new Course("CS101", "Introduction to Programming", 3);
        Professor professor1 = new Professor("P1001", "Dr. Smith", "Computer Science");

        // Creating Enrollment
        Enrollment enrollment1 = new Enrollment(student1, course1, professor1);

        // Displaying enrollment information
        enrollment1.displayEnrollmentInfo();

        // Create another Student, Course, and Professor
        Student student2 = new Student("S1002", "Bob Brown", "Mathematics");
        double[] grades2 = {78, 82, 80, 75, 88}; // Example grades for Bob
        student2.setGrades(grades2);

        Course course2 = new Course("MATH101", "Calculus I", 4);
        Professor professor2 = new Professor("P1002", "Dr. White", "Mathematics");

        // Creating Enrollment for second student
        Enrollment enrollment2 = new Enrollment(student2, course2, professor2);

        // Displaying second enrollment information
        enrollment2.displayEnrollmentInfo();
    }
}