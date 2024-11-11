package launcher;

import database.DatabaseConnectionFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Launcher extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory.getInstance(false, primaryStage);
    }
}