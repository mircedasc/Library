import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
