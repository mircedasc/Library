package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            Long price = bookView.getPrice();
            Long quantity = bookView.getQuantity();

            if (title.isEmpty() || author.isEmpty()){
                bookView.displayAlertMessage("Save Error", "Problem at Title or Author fields", "Can not have empty Title or Author field!");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setPrice(price).setQuantity(quantity).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    bookView.displayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent>{
            @Override
            public void handle(ActionEvent event) {
                BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
                if (bookDTO != null){
                    boolean saleSuccessfull = bookService.sale(BookMapper.convertBookDTOToBook(bookDTO), 1);
                    if (saleSuccessfull){
                        bookView.displayAlertMessage("Sale Successful", "One Book Sold", "Book was successfully sold");
                        bookView.handleSale(BookMapper.convertBookListToBookDTOList(bookService.findAll()));
                        if(bookDTO.getQuantity() < 1){
                            bookView.removeBookFromObservableList(bookDTO);
                        }
                    } else {
                        bookView.displayAlertMessage("Sale not successful", "Sale Process", "There was a problem in the sale process. Please restart the application and try again!");
                    }
                } else {
                    bookView.displayAlertMessage("Sale not successful", "Sale Process", "You need to select a row from table before pressing the sale button!");
                }
            }
    }

}