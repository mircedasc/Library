package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import mapper.UserMapper;
import service.book.BookService;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.BookDTO;
import view.model.UserDTO;
import view.model.builder.BookDTOBuilder;
import service.sale.SaleService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService userService;
    private final BookService bookService;
    private final SaleService saleService;
    public AdminController(AdminView adminView, AuthenticationService userService, BookService bookService, SaleService saleService){
        this.adminView = adminView;
        this.userService = userService;
        this.bookService = bookService;
        this.saleService = saleService;


        this.adminView.addSaveButtonListener(new AdminController.SaveButtonListener());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonListener());
        this.adminView.addSaleButtonListener(new AdminController.SaleButtonListener());
        this.adminView.addDeleteUserButtonListener(new AdminController.DeleteUserButtonListener());
        this.adminView.addReportButtonListener(new AdminController.ReportButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = adminView.getTitle();
            String author = adminView.getAuthor();
            Long price = adminView.getPrice();
            Long quantity = adminView.getQuantity();

            if (title.isEmpty() || author.isEmpty()){
                adminView.displayAlertMessage("Save Error", "Problem at Title or Author fields", "Can not have empty Title or Author field!");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setPrice(price).setQuantity(quantity).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    adminView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    adminView.addBookToObservableList(bookDTO);
                } else {
                    adminView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) adminView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    adminView.displayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    adminView.removeBookFromObservableList(bookDTO);
                } else {
                    adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) adminView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean saleSuccessfull = bookService.sale(BookMapper.convertBookDTOToBook(bookDTO), 1);
                if (saleSuccessfull){
                    adminView.displayAlertMessage("Sale Successful", "One Book Sold", "Book was successfully sold");
                    //bookView.handleSale(BookMapper.convertBookListToBookDTOList(bookService.findAll()));
                    bookDTO.setQuantity(bookDTO.getQuantity() - 1);

                    adminView.handleSale(bookDTO);
                    if(bookDTO.getQuantity() < 1){
                        adminView.removeBookFromObservableList(bookDTO);
                    }
                } else {
                    adminView.displayAlertMessage("Sale not successful", "Sale Process", "There was a problem in the sale process. Please restart the application and try again!");
                }
            } else {
                adminView.displayAlertMessage("Sale not successful", "Sale Process", "You need to select a row from table before pressing the sale button!");
            }
        }
    }

    private class DeleteUserButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            UserDTO userDTO = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();
            if (userDTO != null){
                boolean deletionSuccessfull = userService.delete(userDTO.getUsername());
                if (deletionSuccessfull){
                    adminView.displayAlertMessage("Delete Successful", "User Deleted", "User was successfully deleted from the database.");
                    adminView.removeUserFromObservableList(userDTO);
                } else {
                    adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process!");
                }
            } else {
                adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class ReportButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent){
            Path filePath = Paths.get("E:/a3/is/LibraryProject/sales_report.pdf");
            //System.out.println(saleService.findAll());
            //System.out.println("da");
            saleService.writeToPdf(saleService.findAll(), filePath);

        }
    }
}
