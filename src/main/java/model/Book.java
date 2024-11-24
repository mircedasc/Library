package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;
    private Long price;
    private Long quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getPrice() {return price;}

    public void setPrice(Long price) {this.price = price;}

    public Long getQuantity() {return quantity;}

    public void setQuantity(Long quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "Book: ID: " + id + " Title: " + title + " Author: " + author + " Published Date: " + publishedDate + "Price: " + price + "Amount: " + quantity;

    }
}
