package org.afoninav.service.impl;

import org.afoninav.model.Product;
import org.afoninav.repository.jdbc.JdbcProductRepositoryImpl;
import org.afoninav.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    JdbcProductRepositoryImpl mySqlProductRepository;

    public ProductServiceImpl() {
        mySqlProductRepository = new JdbcProductRepositoryImpl();
    }

    @Override
    public boolean create(Product product) {
        return mySqlProductRepository.create(product);
    }

    @Override
    public Product readById(Long id) {
        return mySqlProductRepository.readById(id);
    }

    @Override
    public List<Product> readAll() {
        return mySqlProductRepository.readAll();
    }

    @Override
    public boolean update(Product product) {
        return mySqlProductRepository.update(product);
    }

    @Override
    public boolean deleteById(Long id) {
        return mySqlProductRepository.deleteByID(id);
    }
}
