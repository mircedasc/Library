package model.builder;

import model.Sale;

import java.time.LocalDate;

public class SaleBuilder {
    private Sale sale;

    public SaleBuilder() {
        sale = new Sale();
    }

    public SaleBuilder setId(Long id) {
        sale.setId(id);
        return this;
    }

    public SaleBuilder setAuthor(String author) {
        sale.setAuthor(author);
        return this;
    }

    public SaleBuilder setTitle(String title) {
        sale.setTitle(title);
        return this;
    }

    public SaleBuilder setSellDate(LocalDate sellDate) {
        sale.setSellDate(sellDate);
        return this;
    }

    public SaleBuilder setEmployeeName(String employeeName) {
        sale.setEmployeeName(employeeName);
        return this;
    }

    public Sale build() {
        return sale;
    }
}
