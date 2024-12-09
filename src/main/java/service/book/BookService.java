package service.book;

import model.Book;

import java.util.*;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);

    boolean delete(Book book);

    boolean sale(Book book, int quantity);

    int getAgeOfBook(Long id);

}