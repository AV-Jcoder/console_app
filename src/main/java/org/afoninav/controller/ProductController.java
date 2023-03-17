package org.afoninav.controller;

import org.afoninav.model.Product;
import org.afoninav.service.ProductService;
import org.afoninav.service.impl.ProductServiceImpl;

import java.util.List;

public class ProductController {

    private ProductService productService;

    public ProductController() {
        productService = new ProductServiceImpl();
    }

    public boolean create(Product product) {
        return productService.create(product);
    }

    public Product readByID(Long id) {
        return productService.readById(id);
    }

    public List<Product> readAll() {
        return productService.readAll();
    }

    public boolean update(Product product) {
        return productService.update(product);
    }

    public boolean deleteById(Long id) {
        return productService.deleteById(id);
    }



}
