package com.ecommerce.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
