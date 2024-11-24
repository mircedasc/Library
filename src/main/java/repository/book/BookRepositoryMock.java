package repository.book;

import model.Book;

import java.util.*;

public class BookRepositoryMock implements BookRepository {
    private final List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public boolean sale(Book book, int quantity) {
        Optional<Book> existingBook = findById(book.getId());
        if(existingBook.isPresent()) {
            if (book.getQuantity() - quantity < 1) {
                return delete(book);
            } else {
                Book bookInList = existingBook.get();
                bookInList.setQuantity(book.getQuantity() - quantity);
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
