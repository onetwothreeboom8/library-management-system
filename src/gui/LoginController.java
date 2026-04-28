package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import network.Client;
import network.Request;
import network.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private final Client client = new Client();

    public void show(Stage stage) {
        Label titleLabel = new Label("Library Management System");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register as Student");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            Map<String, String> data = new HashMap<>();
            data.put("username", usernameField.getText());
            data.put("password", passwordField.getText());

            Response response = client.sendRequest(new Request("LOGIN", data));

            if (response.isSuccess()) {
                User user = (User) response.getData();

                if (user.getRole().equals("ADMIN")) {
                    new AdminController(user).show(stage);
                } else {
                    new StudentController(user).show(stage);
                }
            } else {
                messageLabel.setText(response.getMessage());
            }
        });

        registerButton.setOnAction(e -> {
            Map<String, String> data = new HashMap<>();
            data.put("username", usernameField.getText());
            data.put("password", passwordField.getText());

            Response response = client.sendRequest(new Request("REGISTER", data));
            messageLabel.setText(response.getMessage());
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                titleLabel,
                usernameField,
                passwordField,
                loginButton,
                registerButton,
                messageLabel
        );

        Scene scene = new Scene(root, 350, 250);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}