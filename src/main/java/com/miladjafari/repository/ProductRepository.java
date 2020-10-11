package com.miladjafari.repository;

import com.miladjafari.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, Long> {

    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());
    }

}
