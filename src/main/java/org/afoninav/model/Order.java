package org.afoninav.model;

import java.util.List;
import java.util.Objects;

public class Order {

    private Long id;
    private String comment;
    private List<Product> products;
    private Customer customer;
    private Status status;

    public Order() {
        status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Order order = (Order) o;
        return id.equals(order.id) && customer.equals(order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer);
    }

    @Override
    public String toString() {
        return String.format(
                "\n*********************************\n" +
                "\nOrder{\n" +
                "\tid = %d;\n" +
                "\tcomment = '%s';\n" +
                "\tproducts = %s;\n" +
                "\tstatus = %s;\n" +
                "\tcustomer id = %s;\n" +
                "}\n" +
                "\n*********************************\n", id, comment, products, status, customer.getId()
        );
    }
}
