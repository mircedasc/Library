package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = Optional.of(getBookFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }


    @Override
    public boolean save(Book book) {
        String sql = "INSERT INTO book (author, title, publishedDate, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setLong(4, book.getPrice());
            preparedStatement.setLong(5, book.getQuantity());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean delete(Book book) {
        String sql = "DELETE FROM book WHERE author = ? AND title = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean sale(Book book, int quantity) {
        String updateSql = "UPDATE book SET quantity = ? WHERE author = ? AND title = ?";
        String deleteSql = "DELETE FROM book WHERE author = ? AND title = ?";
        String saveSql = "INSERT INTO sale (author, title, sellDate, employeeName) VALUES (?, ?, ?, ?)";

        boolean success = false;

        try {

            if (book.getQuantity() - quantity < 1) {
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    deleteStatement.setString(1, book.getAuthor());
                    deleteStatement.setString(2, book.getTitle());
                    deleteStatement.executeUpdate();
                    success = true;
                }
            } else {
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setLong(1, book.getQuantity() - quantity);
                    updateStatement.setString(2, book.getAuthor());
                    updateStatement.setString(3, book.getTitle());
                    updateStatement.executeUpdate();
                    success = true;
                }

                try(PreparedStatement saveStatement = connection.prepareStatement(saveSql)){
                    saveStatement.setString(1, book.getAuthor());
                    saveStatement.setString(2, book.getTitle());
                    saveStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    saveStatement.setString(4, "unknown@employee.com");
                    saveStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getLong("price"))
                .setQuantity(resultSet.getLong("quantity"))
                .build();
    }
}