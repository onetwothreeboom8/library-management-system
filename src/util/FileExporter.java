package util;

import model.Book;
import model.Loan;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileExporter {

    public static void exportBooks(List<Book> books, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Library Books Report\n");
            writer.write("====================\n");

            for (Book book : books) {
                writer.write(
                        "ID: " + book.getId() +
                                ", Title: " + book.getTitle() +
                                ", Author: " + book.getAuthor() +
                                ", Genre: " + book.getGenre() +
                                ", Available: " + book.isAvailable() +
                                "\n"
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportLoans(List<Loan> loans, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Library Loans Report\n");
            writer.write("====================\n");

            for (Loan loan : loans) {
                writer.write(
                        "ID: " + loan.getId() +
                                ", User ID: " + loan.getUserId() +
                                ", Book ID: " + loan.getBookId() +
                                ", Borrow Date: " + loan.getBorrowDate() +
                                ", Return Date: " + loan.getReturnDate() +
                                ", Status: " + loan.getStatus() +
                                "\n"
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}