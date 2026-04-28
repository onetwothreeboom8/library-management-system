package network;

import model.Book;
import model.User;
import service.AuthService;
import service.BookService;
import service.LoanService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler extends Thread {
    private final Socket socket;

    private final AuthService authService = new AuthService();
    private final BookService bookService = new BookService();
    private final LoanService loanService = new LoanService();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            Request request = (Request) in.readObject();
            Response response = handleRequest(request);
            out.writeObject(response);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        String action = request.getAction();
        Object data = request.getData();

        switch (action) {
            case "LOGIN": {
                Map<String, String> loginData = (Map<String, String>) data;
                User user = authService.login(
                        loginData.get("username"),
                        loginData.get("password")
                );

                if (user != null) {
                    return new Response(true, "Login successful", user);
                }

                return new Response(false, "Invalid username or password", null);
            }

            case "REGISTER": {
                Map<String, String> registerData = (Map<String, String>) data;
                boolean result = authService.registerStudent(
                        registerData.get("username"),
                        registerData.get("password")
                );

                return new Response(result, result ? "Registered" : "Registration failed", null);
            }

            case "GET_BOOKS":
                return new Response(true, "Books loaded", bookService.getAllBooks());

            case "ADD_BOOK": {
                Book book = (Book) data;
                bookService.addBook(book.getTitle(), book.getAuthor(), book.getGenre());
                return new Response(true, "Book added", null);
            }

            case "DELETE_BOOK": {
                int bookId = (int) data;
                bookService.deleteBook(bookId);
                return new Response(true, "Book deleted", null);
            }

            case "BORROW_BOOK": {
                Map<String, Integer> borrowData = (Map<String, Integer>) data;
                boolean result = loanService.borrowBook(
                        borrowData.get("userId"),
                        borrowData.get("bookId")
                );

                return new Response(result, result ? "Book borrowed" : "Book is not available", null);
            }

            case "RETURN_BOOK": {
                Map<String, Integer> returnData = (Map<String, Integer>) data;
                loanService.returnBook(
                        returnData.get("loanId"),
                        returnData.get("bookId")
                );

                return new Response(true, "Book returned", null);
            }

            case "GET_LOANS":
                return new Response(true, "Loans loaded", loanService.getAllLoans());

            default:
                return new Response(false, "Unknown action", null);
        }
    }
}