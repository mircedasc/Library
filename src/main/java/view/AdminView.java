package view;

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
import view.model.UserDTO;

import java.util.List;

public class AdminView {
    private TableView bookTableView;
    private TableView userTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private final ObservableList<UserDTO> usersObservableList;
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
    private Button deleteUserButton;
    private Button reportButton;

    public AdminView(Stage primaryStage, List<BookDTO> books, List<UserDTO> users) {
        primaryStage.setTitle("Admin View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane, "CENTER");

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        usersObservableList = FXCollections.observableArrayList(users);

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

        userTableView = new TableView<UserDTO>();
        userTableView.setPlaceholder(new Label("No users to display"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTableView.getColumns().addAll(usernameColumn);

        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 3, 5, 1);
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
        gridPane.add(saveButton, 5, 0);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 0);

        saleButton = new Button("Sale");
        gridPane.add(saleButton, 7, 0);

        deleteUserButton = new Button("Delete User");
        gridPane.add(deleteUserButton, 5, 3);

        reportButton = new Button("Create Report");
        gridPane.add(reportButton, 6, 3);

    }

    private void initializeGridPane(GridPane gridPane, String position) {
        gridPane.setAlignment(Pos.valueOf(position));
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

    public void addDeleteUserButtonListener(EventHandler<ActionEvent> deleteUserButtonListener){
        deleteUserButton.setOnAction(deleteUserButtonListener);
    }

    public void addReportButtonListener(EventHandler<ActionEvent> reportButtonListener){
        reportButton.setOnAction(reportButtonListener);
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

    public void handleSale(BookDTO bookDTO) {
        this.booksObservableList.set(booksObservableList.indexOf(bookDTO), bookDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO){this.usersObservableList.remove(userDTO);}

    public TableView getBookTableView() {
        return bookTableView;
    }
    public TableView getUserTableView(){return userTableView;}
}
