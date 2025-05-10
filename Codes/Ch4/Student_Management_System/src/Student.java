class Student {
    private String studentID;
    private String name;
    private String major;
    private double[] grades;

    // Constructor
    public Student(String studentID, String name, String major) {
        this.studentID = studentID;
        this.name = name;
        this.major = major;
        this.grades = new double[5]; // Assuming a student can have 5 grades for simplicity
    }

    // Getter and Setter methods
    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public double[] getGrades() {
        return grades;
    }

    public void setGrades(double[] grades) {
        this.grades = grades;
    }

    // Method to calculate the average grade
    public double calculateAverageGrade() {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    // Method to display student details
    public void displayStudentInfo() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Major: " + major);
        System.out.println("Average Grade: " + calculateAverageGrade());
    }
}