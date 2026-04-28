package service;

import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    public void addBook(String title, String author, String genre) {
        Book book = new Book(0, title, author, genre, true);
        bookDAO.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public void deleteBook(int id) {
        bookDAO.deleteBook(id);
    }
}