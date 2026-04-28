package model;

public class Admin extends User {

    public Admin() {
        this.role = "ADMIN";
    }

    public Admin(int id, String username, String password) {
        super(id, username, password, "ADMIN");
    }

    @Override
    public String getDashboardTitle() {
        return "Admin Dashboard";
    }
}