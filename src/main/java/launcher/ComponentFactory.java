package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static volatile ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTest, Stage stage) {
        if (instance == null) {
            synchronized (ComponentFactory.class) {
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTest, stage);
                }
            }
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(stage, bookDTOs);
        this.bookController = new BookController(bookView, bookService);
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }
}