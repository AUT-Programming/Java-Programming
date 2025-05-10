class Professor {
    private String professorID;
    private String name;
    private String department;

    // Constructor
    public Professor(String professorID, String name, String department) {
        this.professorID = professorID;
        this.name = name;
        this.department = department;
    }

    // Getter methods
    public String getProfessorID() {
        return professorID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    // Method to display professor details
    public void displayProfessorInfo() {
        System.out.println("Professor ID: " + professorID);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
    }
}