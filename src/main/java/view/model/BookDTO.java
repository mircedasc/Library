package view.model;

import javafx.beans.property.*;

public class BookDTO {
    private StringProperty author;
    private StringProperty title;
    private LongProperty price;
    private LongProperty quantity;

    // Author property
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

    // Title property
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

    // Price property (LongProperty instead of StringProperty)
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

    // Quantity property (LongProperty instead of StringProperty)
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
