package launcher;

import controller.AdminController;
import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import mapper.UserMapper;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sale.SaleService;
import service.sale.SaleServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.AdminView;
import view.BookView;
import view.model.BookDTO;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    //private final BookView bookView;
    //private final BookController bookController;
    private final AdminController adminController;
    private final BookRepository bookRepository;
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookService bookService;
    private final SaleService saleService;
    private final AuthenticationService userService;
    private final AdminView adminView;

    private static volatile AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage stage) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTest, stage);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.saleRepository = new SaleRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.bookService = new BookServiceImpl(bookRepository);
        this.userService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.saleService = new SaleServiceImplementation(saleRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        List<UserDTO> usersDTO = UserMapper.conertUserListToUserDTOList(this.userService.findAll());
        this.adminView = new AdminView(stage, bookDTOs, usersDTO);
        this.adminController = new AdminController(adminView, userService, bookService, saleService);
    }

//    public BookView getBookView() {
//        return bookView;
//    }
//
//    public BookController getBookController() {
//        return bookController;
//    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository(){
        return rightsRolesRepository;
    }

    public static AdminComponentFactory getInstance() {
        return instance;
    }
}
