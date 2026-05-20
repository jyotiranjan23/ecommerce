package com.ecommerce.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
       "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Product> searchProducts(@Param("keyword") String keyword);

}
