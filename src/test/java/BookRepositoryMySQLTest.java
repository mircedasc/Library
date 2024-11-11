import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {
    private static BookRepository bookRepository;

    @BeforeAll
    public static void setupClass() {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
    }

    @Test
    public void removeAll() {
        bookRepository.removeAll();
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void save() {
        assertTrue(bookRepository.save(new BookBuilder()
                .setAuthor("Ion Slavici")
                .setTitle("Moara cu nororc")
                .setPublishedDate(LocalDate.of(1950, 2, 10))
                .build()));
        assertEquals(1, bookRepository.findAll().size());
    }

    @Test
    public void delete() {
        int previousSize = bookRepository.findAll().size();
        Book book = new BookBuilder()
                .setAuthor("J.K. Rowling")
                .setTitle("Harry Potter")
                .setPublishedDate(LocalDate.of(1883, 1, 15))
                .build();

        bookRepository.save(book);
        bookRepository.delete(book);

        assertEquals(previousSize, bookRepository.findAll().size());
    }
}
