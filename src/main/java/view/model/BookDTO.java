package view.model;

import javafx.beans.property.*;

public class BookDTO {
    private StringProperty author;
    private StringProperty title;
    private LongProperty price;
    private LongProperty quantity;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if (author == null) {
            author = new SimpleStringProperty(this, "author");
        }

        return author;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }

        return title;
    }

    public void setPrice(Long price) {
        priceProperty().set(price);
    }

    public Long getPrice() {
        return priceProperty().get();
    }

    public LongProperty priceProperty() {
        if (price == null) {
            price = new SimpleLongProperty(this, "price");
        }

        return price;
    }

    public void setQuantity(Long quantity) {
        quantityProperty().set(quantity);
    }

    public Long getQuantity() {
        return quantityProperty().get();
    }

    public LongProperty quantityProperty() {
        if (quantity == null) {
            quantity = new SimpleLongProperty(this, "quantity");
        }

        return quantity;
    }
}
