package service;

import dao.BookDAO;
import dao.LoanDAO;
import model.Book;
import model.Loan;

import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO = new LoanDAO();
    private final BookDAO bookDAO = new BookDAO();

    public boolean borrowBook(int userId, int bookId) {
        if (!isBookAvailable(bookId)) {
            return false;
        }

        loanDAO.borrowBook(userId, bookId);
        bookDAO.updateAvailability(bookId, false);
        return true;
    }

    public void returnBook(int loanId, int bookId) {
        loanDAO.returnBook(loanId);
        bookDAO.updateAvailability(bookId, true);
    }

    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    public boolean isBookAvailable(int bookId) {
        List<Book> books = bookDAO.getAllBooks();

        for (Book book : books) {
            if (book.getId() == bookId) {
                return book.isAvailable();
            }
        }

        return false;
    }
}