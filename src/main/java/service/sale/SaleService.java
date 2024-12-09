package service.sale;

import model.Sale;

import java.nio.file.Path;
import java.util.List;

public interface SaleService {
    List<Sale> findAll();

    void writeToPdf(List<Sale> valuesToExport, Path filePath);

}
