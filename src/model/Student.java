package model;

public class Student extends User {

    public Student() {
        this.role = "STUDENT";
    }

    public Student(int id, String username, String password) {
        super(id, username, password, "STUDENT");
    }

    @Override
    public String getDashboardTitle() {
        return "Student Dashboard";
    }
}