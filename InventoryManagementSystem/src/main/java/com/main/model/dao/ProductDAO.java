package com.main.model.dao;

import com.main.model.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductDAO implements DataAccessObject<ProductEntity> {

    private List<ProductEntity> list = new ArrayList<>();

    @Override
    public Optional<ProductEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<ProductEntity> getAll() {
        return list;
    }

    @Override
    public void save(ProductEntity productEntity) {
        list.add(productEntity);
    }

    @Override
    public void update(ProductEntity productEntity, String[] params) {
        productEntity.setName(Objects.requireNonNull(params[0], "idProduct cannot be null"));
        productEntity.setPrice(Double.parseDouble(params[1]));
        productEntity.setDescription(params[2] != null ? params[2] : "Empty");
        list.add(productEntity);
    }

    @Override
    public void delete(ProductEntity productEntity) {
        list.remove(productEntity);
    }
}
