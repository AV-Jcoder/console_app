package org.afoninav.model;

import java.util.List;
import java.util.Objects;

public class Customer {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private Status status;

    private List<Order> orders;

    public Customer() {
        status = Status.ACTIVE;
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        status = Status.ACTIVE;
    }

    public Customer(Long id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && name.equals(customer.name) && phone.equals(customer.phone) && email.equals(customer.email) && Objects.equals(address, customer.address) && Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, email, address, orders);
    }

    @Override
    public String toString() {
        return String.format(
                "\nCustomer{\n" +
                "\tid = %d;\n" +
                "\tname = '%s';\n" +
                "\tphone = '%s';\n" +
                "\temail = '%s';\n" +
                "\taddress = '%s';\n" +
                "\tstatus = '%s';\n" +
                "\torders =\n%s;\n" +
                "}\n", id, name, phone, email, address, status, getAllOrdersIds()
        );
    }

    private String getAllOrdersIds() {
        StringBuilder sb = new StringBuilder();
        for (Order o : orders) {
            sb.append(o.getId()).append(", ").append(o.getComment()).append(", ").append(o.getStatus()).append("\n");
        }
        return sb.toString();
    }
}
