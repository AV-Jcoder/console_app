package org.afoninav.repository.jdbc;

import org.afoninav.model.Product;
import org.afoninav.repository.ProductRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcProductRepositoryImpl implements ProductRepository {

    @Override
    public boolean create(Product product) {
        if (productIsExists(product)) {
            return false;
        }
        int count = 0;
        Long id = getNextId();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_CREATE.QUERY)) {
            statement.setLong(1,id);
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            count  = statement.executeUpdate(); // 0 or 1
        } catch (SQLException e) {
            e.printStackTrace();
        }
        product.setId(id);
        return count != 0;
    }

    @Override
    public Product readById(Long id) {
        Product product = null;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_READ_BY_ID.QUERY)) {
            statement.setLong(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean update(Product product) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_UPDATE.QUERY)) {
            statement.setString(1, product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setLong(3, product.getId());
            count = statement.executeUpdate(); // 0 or 1
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public boolean deleteByID(Long id) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_DELETE_BY_ID.QUERY)) {
            statement.setLong(1, id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public List<Product> readAll() {
        List<Product> products = new LinkedList<>();
        try(Connection connection = JdbcConnector.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery((SQLNative.PRODUCT_READ_ALL.QUERY));
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private boolean productIsExists(Product product) {
        int recordsCount = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_GET_COUNT.QUERY)) {
            statement.setString(1, product.getName());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                recordsCount = res.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsCount != 0;
    }

    private Long getNextId() {
        Long id = null;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.PRODUCT_GET_NEXT_ID.QUERY)) {
            ResultSet result  = statement.executeQuery();
            if (result.next()) {
                id = result.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
