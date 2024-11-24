package view;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.BookDTO;

import java.util.*;


public class BookView {
    private TableView bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField quantityTextField;

    private Label authorLabel;
    private Label titleLabel;
    private Label priceLabel;
    private Label quantityLabel;

    private Button saveButton;
    private Button deleteButton;
    private Button saleButton;

    public BookView(Stage primaryStage, List<BookDTO> books) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);

        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, String> priceColumn = new TableColumn<BookDTO, String>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<BookDTO, String> quantityColumn = new TableColumn<BookDTO, String>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, quantityColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 2, 2);

        quantityLabel = new Label("Quantity");
        gridPane.add(quantityLabel, 3, 2);

        quantityTextField = new TextField();
        gridPane.add(quantityTextField, 4, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);

        saleButton = new Button("Sale");
        gridPane.add(saleButton, 7, 1);

    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSaleButtonListener(EventHandler<ActionEvent> saleButtonListener) {
        saleButton.setOnAction(saleButtonListener);
    }

    public void displayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public Long getPrice(){return Long.parseLong(priceTextField.getText());}
    public Long getQuantity(){return Long.parseLong(quantityTextField.getText());}


    public ObservableList<BookDTO> getBooksObservableList() {
        return booksObservableList;
    }


    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public void handleSale(List<BookDTO> books) { this.booksObservableList.setAll(FXCollections.observableArrayList(books));}

    public TableView getBookTableView() {
        return bookTableView;
    }


}
