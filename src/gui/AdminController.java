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

import java.util.List;

public class AdminController {

    private final User user;
    private final Client client = new Client();

    public AdminController(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label title = new Label("Admin Panel");

        TableView<Book> table = new TableView<>();

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuthor()));

        table.getColumns().addAll(titleCol, authorCol);

        Button loadBtn = new Button("Load Books");
        Button addBtn = new Button("Add Book");
        Button deleteBtn = new Button("Delete Selected");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        loadBtn.setOnAction(e -> {
            Response response = client.sendRequest(new Request("GET_BOOKS", null));

            if (response.isSuccess()) {
                List<Book> books = (List<Book>) response.getData();
                table.setItems(FXCollections.observableArrayList(books));
            }
        });

        addBtn.setOnAction(e -> {
            Book book = new Book(0,
                    titleField.getText(),
                    authorField.getText(),
                    genreField.getText(),
                    true);

            client.sendRequest(new Request("ADD_BOOK", book));
        });

        deleteBtn.setOnAction(e -> {
            Book selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                client.sendRequest(new Request("DELETE_BOOK", selected.getId()));
            }
        });

        VBox root = new VBox(10,
                title,
                table,
                titleField,
                authorField,
                genreField,
                addBtn,
                deleteBtn,
                loadBtn
        );

        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("Admin");
        stage.show();
    }
}