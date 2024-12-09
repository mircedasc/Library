package repository.sale;

import model.Sale;
import model.builder.SaleBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryMySQL implements SaleRepository{
    private final Connection connection;

    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Sale> findAll() {
        String sql = "SELECT * FROM sale;";

        List<Sale> sales = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                sales.add(getSaleFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    private Sale getSaleFromResultSet(ResultSet resultSet) throws SQLException {
        return new SaleBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setSellDate(new java.sql.Date(resultSet.getDate("sellDate").getTime()).toLocalDate())
                .setEmployeeName(resultSet.getString("employeeName"))
                .build();
    }
}

