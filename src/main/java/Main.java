import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import service.book.BookService;
import service.book.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Book book = new BookBuilder().setAuthor("Me")
                .setTitle("Harry Potter")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        bookRepository.save((book));

        System.out.println(bookService.findAll());
        System.out.println(bookService.findAll());

    }
}
