package service;

import dao.UserDAO;
import model.Student;
import model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        return userDAO.login(username, password);
    }

    public boolean registerStudent(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        Student student = new Student(0, username, password);
        userDAO.register(student);
        return true;
    }

}
