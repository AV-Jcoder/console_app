package org.afoninav.model;

import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private double price;

    public Product() {}

    public Product(Long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return String.format(
                "\nProduct{\n" +
                "\tid = %d;\n" +
                "\tname = '%s';\n" +
                "\tprice = %.2f;\n" +
                "}\n",id, name, price);
    }
}
