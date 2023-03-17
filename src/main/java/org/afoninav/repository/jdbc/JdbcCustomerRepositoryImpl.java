package org.afoninav.repository.jdbc;

import org.afoninav.model.Customer;
import org.afoninav.model.Order;
import org.afoninav.repository.CustomerRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcCustomerRepositoryImpl implements CustomerRepository {

    private final static StatusBooleanConverter converter = new StatusBooleanConverter();

    @Override
    public boolean create(Customer customer) {
        if (customerIsExists(customer)) {
            return false;
        }
        int count = 0;
        Long id = getNextId();
        customer.setId(id);
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.CUSTOMER_CREATE.QUERY)) {
            statement.setLong(1, id);
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public Customer readById(Long id) {
        Customer customer = null;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.CUSTOMER_READ_BY_ID.QUERY)) {
            statement.setLong(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                customer = new Customer();
                customer.setId(id);
                customer.setName(res.getString("name"));
                customer.setPhone(res.getString("phone"));
                customer.setEmail(res.getString("email"));
                customer.setAddress(res.getString("address"));
                customer.setStatus(converter.convertToJavaClassField(res.getBoolean("status")));
                customer.setOrders(getAllOrdersByCustomerId(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean update(Customer customer) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.CUSTOMER_UPDATE.QUERY)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setLong(5, customer.getId());
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public boolean deleteByID(Long id) {
        int count = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.CUSTOMER_CHANGE_STATUS.QUERY)) {
            statement.setLong(1,id);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    @Override
    public List<Customer> readAll() {
        List<Customer> customers = new LinkedList<>();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLNative.CUSTOMER_READ_ALL.QUERY)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Customer customer = new Customer();
                customer.setId(res.getLong("customer_id"));
                customer.setName(res.getString("name"));
                customer.setPhone(res.getString("phone"));
                customer.setEmail(res.getString("email"));
                customer.setAddress(res.getString("address"));
                customer.setStatus(converter.convertToJavaClassField(res.getBoolean("status")));
                customer.setOrders(getAllOrdersByCustomerId(customer.getId()));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private boolean customerIsExists(Customer customer) {
        int recordsCount = 0;
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement stat = connection.prepareStatement(SQLNative.CUSTOMER_GET_COUNT.QUERY)) {
            stat.setString(1, customer.getPhone());
            stat.executeQuery();
            ResultSet res = stat.executeQuery();
            if (res.next()) {
                recordsCount = res.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsCount != 0;
    }

    private Long getNextId() {
        Long id = 0L;
        try(Connection connection = JdbcConnector.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(SQLNative.CUSTOMER_GET_NEXT_ID.QUERY);
            if (res.next()) {
                id = res.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private List<Order> getAllOrdersByCustomerId(Long id) {
        List<Order> orders = new LinkedList<>();
        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SQLNative.CUSTOMER_GET_ALL_ORDERS_BY_CUSTOMER_ID.QUERY)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("order_id"));
                order.setComment(resultSet.getString("comment"));
                order.setStatus(converter.convertToJavaClassField(resultSet.getBoolean("status")));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
