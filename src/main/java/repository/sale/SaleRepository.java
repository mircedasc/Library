package repository.sale;

import model.Sale;

import java.util.*;
public interface SaleRepository {
    List<Sale> findAll();
}
