import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMockTest {
    private static BookRepository bookRepository;

    @BeforeAll
    public static void setupClass() {
        bookRepository = new BookRepositoryMock();
    }

    @Test
    public void findAll() {
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void findById() {
        final Optional<Book> books = bookRepository.findById(1L);
        assertTrue(books.isEmpty());
    }

    @Test
    public void save() {
        assertTrue(bookRepository.save(new BookBuilder()
                .setAuthor("Ion Slavici")
                .setTitle("Moara cu nororc")
                .setPublishedDate(LocalDate.of(1950, 2, 10))
                .setPrice(20L)
                .setQuantity(2L)
                .build()));
    }
}
