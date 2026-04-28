package gui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.User;
import network.Client;
import network.Request;
import network.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentController {

    private final User user;
    private final Client client = new Client();

    public StudentController(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label title = new Label("Student Panel");

        TableView<Book> table = new TableView<>();

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));

        table.getColumns().add(titleCol);

        Button loadBtn = new Button("Load Books");
        Button borrowBtn = new Button("Borrow Book");

        loadBtn.setOnAction(e -> {
            Response response = client.sendRequest(new Request("GET_BOOKS", null));

            if (response.isSuccess()) {
                List<Book> books = (List<Book>) response.getData();
                table.setItems(FXCollections.observableArrayList(books));
            }
        });

        borrowBtn.setOnAction(e -> {
            Book selected = table.getSelectionModel().getSelectedItem();

            if (selected != null) {
                Map<String, Integer> data = new HashMap<>();
                data.put("userId", user.getId());
                data.put("bookId", selected.getId());

                client.sendRequest(new Request("BORROW_BOOK", data));
            }
        });

        VBox root = new VBox(10, title, table, loadBtn, borrowBtn);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Student");
        stage.show();
    }
}