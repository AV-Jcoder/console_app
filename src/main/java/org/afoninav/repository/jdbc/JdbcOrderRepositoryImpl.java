package org.afoninav.repository.jdbc;

import org.afoninav.model.Order;
import org.afoninav.model.Product;
import org.afoninav.repository.OrderRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcOrderRepositoryImpl implements OrderRepository {

    private final static StatusBooleanConverter converter = new StatusBooleanConverter();
    private final static JdbcCustomerRepositoryImpl customerRepository = new JdbcCustomerRepositoryImpl();
    private final static JdbcProductRepositoryImpl productRepository = new JdbcProductRepositoryImpl();

    @Override
    public boolean create(Order order) {
        int count = 0;
        boolean detailsIsPersist = false;
        Long id = getId();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLNative.ORDER_CREATE.QUERY)){
            ps.setLong(1, id);
            ps.setLong(2, order.getCustomer().getId());
            ps.setString(3, order.getComment());
            count = ps.executeUpdate();
            detailsIsPersist = createNewOrderDetailsByOrderId(id, order.getProduct());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        order.setId(id);
        return count != 0 && detailsIsPersist;
    }

    @Override
    public Order readById(Long id) {
        Order order = null;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLNative.ORDER_READ_BY_ID.QUERY)) {
            ps.setLong(1,id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                order = new Order();
                order.setId(result.getLong("order_id"));
                order.setComment(result.getString("comment"));
                order.setStatus(converter.convertToJavaClassField(result.getBoolean("status")));
                order.setCustomer(customerRepository.readById(result.getLong("customer_id")));
                order.setProduct(getProductsByOrderId(order.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean update(Order order) {
        int count = 0;
        boolean detailsIsPersist = false;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_UPDATE.QUERY)) {
            statement.setString(1,order.getComment());
            statement.setBoolean(2, converter.convertToDataBaseAttribute(order.getStatus()));
            statement.setLong(3, order.getCustomer().getId());
            statement.setLong(4, order.getId());
            count = statement.executeUpdate();
            detailsIsPersist = persistAllProductsByOrderId(order.getId(), order.getProduct());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0 && detailsIsPersist;
    }

    @Override
    public boolean deleteByID(Long id) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_CHANGE_STATUS.QUERY)) {
            statement.setLong(1, id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public List<Order> readAll() {
        List<Order> orders = new LinkedList<>();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_READ_ALL.QUERY)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getLong("order_id"));
                order.setComment(result.getString("comment"));
                order.setCustomer(customerRepository.readById(result.getLong("customer_id")));
                order.setStatus(converter.convertToJavaClassField(result.getBoolean("status")));
                order.setProduct(getProductsByOrderId(order.getId()));
                order.setProduct(getProductsByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Long getId() {
        Long id = 0L;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_GET_NEXT_ID.QUERY)) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                id = result.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private List<Product> getProductsByOrderId(Long id) {
        List<Product> products = new LinkedList<>();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_GET_PRODUCTS_BY_ORDER_ID.QUERY)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getLong("product_id"));
                product.setName(result.getString("name"));
                product.setPrice(result.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private boolean persistAllProductsByOrderId(Long id, List<Product> products) {
        return eraseOrderDetailsByOrderId(id)
                && createNewOrderDetailsByOrderId(id, products);
    }

    private boolean eraseOrderDetailsByOrderId(Long id) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.ORDER_ERASE_ORDER_DETAILS_BY_ORDER_ID.QUERY)) {
            statement.setLong(1,id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    private boolean createNewOrderDetailsByOrderId(Long id, List<Product> products) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SQLNative.ORDER_CREATE_NEW_ORDER_DETAILS_BY_ORDER_ID.QUERY)) {
            for (int i = 0; i < products.size(); i++) {
                statement.setLong(1,id);
                statement.setLong(2,products.get(i).getId());
                count += statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == products.size();
    }
}
